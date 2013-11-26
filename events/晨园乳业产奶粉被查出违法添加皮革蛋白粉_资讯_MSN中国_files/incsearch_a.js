if (navigator.userAgent.indexOf("Windows NT 6.0") > -1 || navigator.userAgent.indexOf("Windows NT 6.1") > -1) {
    var ie_down = document.getElementById("ie_down");
    if (ie_down) { ie_down.innerHTML = "免费IE9"; ie_down.href = "http://info.msn.com.cn/ie9/"; }
    document.writeln("<a href=\"http:\/\/info.msn.com.cn\/ie9\/\" target='_blank'><img src=\"http:\/\/img2.auto.msn.com.cn\/images\/2011\/220X92_2.gif\" style='border:none' alt=\"立即下载优化版IE9\"\/></a>");
}
else
    document.writeln("<a href=\"http:\/\/info.msn.com.cn\/ie8\/\" target='_blank'><img src=\"http:\/\/img8.cn.msn.com\/images\/downie8.jpg\" alt=\"立即下载优化版IE8\"\/></a>");
