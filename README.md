# instafbchecker - Instagram no life guide

![Platerform](https://shields.io/badge/platform-linux--64%20|%20win--64-informational)
![Build](https://img.shields.io/badge/Built%20using-Kotlin-informational)
![Kotlin Version](https://img.shields.io/badge/kotlin-v1.6.21-yellowgreen)
![Gradle Version](https://img.shields.io/badge/gradle-v7.3.2-yellowgreen)
![License](https://img.shields.io/badge/license-GNU_General_Public_License-_red.svg)

**Current Release: v1.0.1 (30/08/2022)**

A command line tool used to check which users dont follow you back on **Instagram**.

**instafbchecker** is mainly developed by **[Pseudow](https://github.com/Pseudow)**.

It is a pure troll even though **it works**!

# Warning
This tool requires an **Instagram account** to work. You have to pass your account information such
as **password** and **username** in the command line. We do not **assure the security of the process** since
this tool uses a [library to communicate with Instagram api](https://github.com/ErrorxCode/EasyInsta).
### Although it works, we are not responsible if your account get suspended or anything else. We let an option which can help you avoid that -interval, but we are still not responsible.

# Usage
```
Usage: instafbchecker username password options_list
Arguments: 
    username -> The username of the account { String }
    password -> The password of the account { String }
Options: 
    --format, -f [TEXT] -> Format for output { Value should be one of [text] }
    --target, -T [] -> The target to use this command (By default if not provided it is the user itself) { String }
    --interval, -i [3] -> The interval between each request (Used to avoid being blocked by the API) { Int }
    --timeout, -t [20] -> Max timeout for each request { Int }
    --debug, -d [false] -> Turn on debug mode 
    --help, -h -> Usage info 
```

Only one output format is currently supported which is **old plain text** named as text.

# Contribution
See [CONTRIBUTORS.md](https://github.com/Pseudow/instafbchecker/blob/master/CONTRIBUTORS.md) to know who they are.

#### Pull requests and feature requests are welcomed

# License
Copyright (C) Nathan Ogueton (pseudow96@proton.me)

License: GNU General Public License, version 2