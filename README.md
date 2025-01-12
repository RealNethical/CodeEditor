# CodeEditor
[![GitHub license](https://img.shields.io/github/license/Rosemoe/CodeEditor)](https://github.com/Rosemoe/CodeEditor/blob/main/LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.Rosemoe.CodeEditor/editor.svg?label=Maven%20Central)]((https://search.maven.org/search?q=io.github.Rosemoe.CodeEditor%20editor))
[![Telegram](https://img.shields.io/badge/Join-Telegram-blue)](https://t.me/rosemoe_code_editor)
[![QQ](https://img.shields.io/badge/Join-QQ_Group-ff69b4)](https://jq.qq.com/?_wv=1027&k=n68uxQws)   
A cool and optimized code editor on Android platform with good performance and nice features.
--
***Work In Progress*** This project is still developing slowly. Note that APIs are unstable.
It is **not** recommended to use this project for production use.
Download newest sources from [Releases](https://github.com/Rosemoe/CodeEditor/releases) instead of cloning this repository directly.
**Issues and pull requests are welcome.**
## Features
- [x] Smooth syntax highlight
- [x] Auto completion
- [x] Auto indent
- [x] Code block lines
- [x] Scale text
- [x] Select text
- [x] Scroll, Scrollbars, EdgeEffect, OverScroll
- [x] Undo/redo
- [x] Search and replace
- [x] Shortcuts
- [x] Auto wordwrap
- [x] Show non-printable characters
- [ ] Incremental highlight Analysis
- [ ] Plugin System
## Language Supported  
* Java, JavaScript, C, C++, HTML, Python (Basic Support:highlight, code block line,identifier and keyword auto-completion). Code block line isn't available for HTML Language
## Screenshots
![Wordwrap](/images/wordwrap.png)
## How to use this editor  
Add to your app's dependencies:
```Gradle
implementation 'io.github.Rosemoe.CodeEditor:<moduleName>:<versionName>'
```
Available modules:     
* editor 
* language-base
* language-java
* language-html
* language-python
* language-universal
## License
```
    CodeEditor - the awesome code editor for Android
    Copyright (C) 2020-2021  Rosemoe

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

    Please contact Rosemoe by email roses2020@qq.com if you need
    additional information or have any questions
```
## Discuss
* Official QQ Group:[216632648](https://jq.qq.com/?_wv=1027&k=n68uxQws)
* [Official Telegram Group](https://t.me/rosemoe_code_editor)
## Acknowledgements
Thanks to [JetBrains](https://www.jetbrains.com/?from=CodeEditor) for allocating free open-source licences for IDEs such as [IntelliJ IDEA](https://www.jetbrains.com/idea/?from=CodeEditor).
[<img src=".github/jetbrains-variant-3.png" width="200"/>](https://www.jetbrains.com/?from=CodeEditor)