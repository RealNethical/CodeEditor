/*
 *    CodeEditor - the awesome code editor for Android
 *    Copyright (C) 2020-2021  Rosemoe
 *
 *     This library is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU Lesser General Public
 *     License as published by the Free Software Foundation; either
 *     version 2.1 of the License, or (at your option) any later version.
 *
 *     This library is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public
 *     License along with this library; if not, write to the Free Software
 *     Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301
 *     USA
 *
 *     Please contact Rosemoe by email 2073412493@qq.com if you need
 *     additional information or have any questions
 */
package io.github.rosemoe.editor.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.lang.Thread.UncaughtExceptionHandler;

import android.content.pm.PackageManager.NameNotFoundException;

import io.github.rosemoe.editor.app.R;

/**
 * CrashHandler handles uncaught exceptions
 * And force the main thread continue to work
 *
 * @author Rose
 */
@SuppressWarnings("CanBeFinal")
public class CrashHandler implements UncaughtExceptionHandler {

    public final static String LOG_TAG = "CrashHandler";
    public final static CrashHandler INSTANCE = new CrashHandler();

    private Context mContext;
    private Map<String, String> info = new HashMap<>();

    private CrashHandler() {
    }

    public void init(Context context) {
        mContext = context.getApplicationContext();
        collectDeviceInfo(mContext);
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        saveCrashInfo(thread.getName(), ex);
        // Save the world, hopefully
        if (Looper.myLooper() != null) {
            while (true) {
                try {
                    Toast.makeText(mContext, R.string.err_crash_loop, Toast.LENGTH_SHORT).show();
                    Looper.loop();
                } catch (Throwable t) {
                    saveCrashInfo(thread.getName(), t);
                }
            }
        }
    }

    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                info.put("versionName", versionName);
                info.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
            Log.e(LOG_TAG, "an error occurred while collecting package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object obj = field.get(null);
                if (obj instanceof String[]) {
                    info.put(field.getName(), Arrays.toString((String[]) obj));
                } else {
                    info.put(field.getName(), String.valueOf(obj));
                }
            } catch (Exception e) {
                Log.e(LOG_TAG, "an error occurred while collecting crash info", e);
            }
        }
        fields = Build.VERSION.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object obj = field.get(null);
                if (obj instanceof String[]) {
                    info.put(field.getName(), Arrays.toString((String[]) obj));
                } else {
                    info.put(field.getName(), String.valueOf(obj));
                }
            } catch (Exception e) {
                Log.e(LOG_TAG, "an error occurred while collecting crash info", e);
            }
        }
    }

    private void saveCrashInfo(String threadName, Throwable ex) {
        StringBuilder sb = new StringBuilder();
        long timestamp = System.currentTimeMillis();
        sb.append("Crash at ").append(timestamp).append("(timestamp) in thread named '").append(threadName).append("'\n");
        sb.append("Local date and time:").append(SimpleDateFormat.getDateTimeInstance().format(new Date(timestamp))).append('\n');
        for (Map.Entry<String, String> entry : info.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append("=").append(value).append("\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result).append('\n');
        try {
            Log.e(LOG_TAG, sb.toString());
            FileOutputStream fos = mContext.openFileOutput("crash-journal.log", Context.MODE_APPEND);
            fos.write(sb.toString().getBytes());
            fos.close();
        } catch (Exception e) {
            Log.e(LOG_TAG, "an error occurred while writing file...", e);
        }
    }
}



