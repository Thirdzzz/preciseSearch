(function(){if(!window._AC){var g=window.navigator.userAgent.toLowerCase(),c=window._AC=function(a,b,d,e,f,h,k){return arguments.length===2?new c.prototype.init2(a,b):new c.prototype.init(a,b,d,e,f,h,k)};c.extend=function(a,b){for(var d in b)if(b.hasOwnProperty(d))a[d]=b[d];return a};c.extend(c,{sfp:!(window.ActiveXObject&&document.compatMode=="BackCompat"||window.ActiveXObject&&g.match(/msie[ ]([\d.]+)/)[1]<=6),getFixedPosition:function(){var a="fixed";if(c.sfp)a="absolute";return a},$:function(a){return typeof a==
"string"?document.getElementById(a):a},$anch:function(a){return document.getElementById("ac_js86_"+a)},span:function(a,b){var d=document.createElement("span");if(a)d.id=a;b&&c.css(d,b);return d},getClient:function(a){a=a.charAt(0).toUpperCase()+a.substr(1).toLowerCase();return document.compatMode=="BackCompat"?document.body["client"+a]:document.documentElement["client"+a]},getScroll:function(a){a=a.charAt(0).toUpperCase()+a.substr(1).toLowerCase();return document.documentElement["scroll"+a]||document.body["scroll"+
a]},getPosition:function(a){a=c.$(a);var b={left:0,top:0};if(a.getBoundingClientRect){b.left=Math.round(a.getBoundingClientRect().left+c.getScroll("left")-(document.documentElement.clientLeft||document.body.clientLeft||0));b.top=Math.round(a.getBoundingClientRect().top+c.getScroll("top")-(document.documentElement.clientTop||document.body.clientTop||0))}return b},random:function(a){a=a||0;if(a==-1)for(var b in c.random){if(c.random[b]!=Object.prototype[b])c.random[b]=null}else{c.random["_"+a]||(c.random["_"+
a]=(new Date).getTime()+Math.round(Math.random()*1E5));return c.random["_"+a]}},css:function(a,b){var d=c.$(a);if(typeof b==="string"){if(d.currentStyle)return d.currentStyle[b];if(window.getComputedStyle)return document.defaultView.getComputedStyle(d,null)[b]}else if(typeof b==="object")for(var e in b)d.style[e]=b[e]},joinParameters:function(a,b){var d=[];if(b===",")for(var e in a)a.hasOwnProperty(e)&&Array.prototype.push.call(d,a[e]);else if(b==="&")for(var f in a)a.hasOwnProperty(f)&&Array.prototype.push.call(d,
f+"="+a[f]);return d.join(b)},send:function(a,b){var d,e=document.getElementsByTagName("script")[0],f="";if(a){if(a.indexOf("//miaozhen.com/")===-1){f="cb="+Math.random();f=a.indexOf("?")>-1?"&"+f:"?"+f}d=b?document.createElement("iframe"):document.createElement("img");d.style.display="none";d.src=a+f;e.parentNode.insertBefore(d,e)}},load:function(a,b,d,e,f){var h,k=/loaded|complete/i,i="ac_script_$"+Math.random();if(b){h=document.createElement("script");h.type="text/javascript";h.async=1;h.id=i;
h.src=a;a=document.getElementsByTagName("script")[0];a.parentNode.insertBefore(h,a)}else{if(window.ActiveXObject)k=/complete/i;document.write('<script type="text/javascript" src="'+a+'" id="'+i+'"><\/script>')}if(h=document.getElementById(i))h.onload=h.onreadystatechange=function(){if(!h.readyState||h.readyState.match(k)){if(typeof d==="function"){d();d=f=null}if(h&&h.parentNode){h.onload=h.onreadystatechange=null;h.parentNode.removeChild(h);h=null}}};e&&setTimeout(function(){if(h){if(typeof f===
"function"){f();d=f=null}h.src="javascript:void(0)"}},e)},convertToXMLDom:function(a){var b;if(window.ActiveXObject){b=new ActiveXObject("Microsoft.XMLDOM");b.loadXML(a)}else b=(new DOMParser).parseFromString(a,"text/xml");return b},addEvent:function(a,b,d){if(a.attachEvent)a.attachEvent("on"+b,d);else a.addEventListener&&a.addEventListener(b,d,false)},removeEvent:function(a,b,d){if(a.detachEvent)a.detachEvent("on"+b,d);else a.removeEventListener&&a.removeEventListener(b,d,false)}});c.prototype={jsv:10,
init:function(a,b,d,e,f,h,k){this.aid=b;this.mid=d;this.ex("mbid")||this.ex("mbid",[]);this.ex("mbid").length>2&&this.ex("mbid").shift();this.ex("mbid").push(d);this.cid=e;this.apm=0;this.params=h?h:{1:{F:[1,0,1,0,"100%","100%","7"]}};a={display:"block",overflow:"hidden",position:"absolute",left:0,top:0,width:f[1]+"px",height:f[2]+"px",margin:0,padding:0,background:"none",border:"0",textAlign:"left"};this.inner=c.span(null,a);a.overflow="auto";this.middler=c.span(null,a);a.overflow="visible";a.marginLeft=
a.marginTop="-10000px";this.outer=c.span("AC_TR86_"+b,a);this.middler.appendChild(this.inner);this.outer.appendChild(this.middler);if(this.apm){this.holder=c.span("AC_HL86_"+b,a);this.append(this.holder);this.insert(this.outer)}else this.append(this.outer);this.setState(k||1);this.C(f[0],f[1],f[2],f[3],f[4],f[5],f[6])},init2:function(a,b){this.aid=a;this.inner=c.span("AC_TR86_"+a);this.append(this.inner);this.C(b)},append:function(a){var b=this.info("destid"),d=c.$anch(this.aid);if(b&&c.$(b))c.$(b).appendChild(a);
else if(d)(b=d.parentNode)&&b.insertBefore(a,d)},insert:function(a){document.body.insertBefore(a,document.body.firstChild)},setState:function(a){if(this.state!=a){this.state=a;a=this.params[a];for(var b in a)if(this[b]&&this[b]!=Object.prototype[b])this[b](a[b])}},report:function(a,b){var d={cb:Math.ceil(Math.random()*1E10)};if(a.indexOf("//acs86.com/")>-1||a.indexOf(this.info("serverbaseurl"))>-1){d.jsv=this.jsv;if(b&&typeof _acG!="undefined"&&_acG.fc)d.fc=_acG.fc();a+=(a.indexOf("?")>0?"&":"?")+
c.joinParameters(d,"&");c.load(a,1)}else{a+=(a.indexOf("?")>0?"&":"?")+c.joinParameters(d,"&");c.send(a)}},C:function(a,b,d,e,f,h,k){e=e?e:"AC_CR86_"+this.aid;f={"0":"opaque","1":"transparent","2":"window",opaque:"opaque",transparent:"transparent",window:"window"}[f?f:"0"];var i=a.toLowerCase().match(/\.(\w{2,4})(?:\/?)(?:\?[^<>]*)?$/),n="http://static.acs86.com/",j="http://acs86.com/";this.creative={id:e,src:a,w:b,h:d};if(this.info("serverbaseurl"))j="http://"+this.info("serverbaseurl");if(this.info("staticbaseurl"))n=
"http://"+this.info("staticbaseurl");if(arguments.length>1){i=i[1];if(/gif|jpg|jpeg|png|bmp/.test(i)){a=a.indexOf("http://")>-1?a:n+a;this.inner.innerHTML='<a href="'+k+'" title="" target="_blank"><img id="'+e+'" src="'+a+'" width="'+b+'" height="'+d+'" alt="" style="border:0;margin:0;padding:0;" /></a>'}else if(/swf/.test(i)){a=a.indexOf("http://")>-1?a:n+a;if(h&&h.indexOf("<cf")>-1){i=c.convertToXMLDom(h.replace(/&/g,"@_@")).getElementsByTagName("cf")[0];var m=i.getAttribute("fw")||"FrameWork/system/Format_1.0.swf";
m&&m.indexOf("http://")!=0&&i.setAttribute("fw",[n,m].join(""));i.setAttribute("ru",[j,"v.htm?pv=1@_@sp=",[this.aid,this.mid,this.cid].join(","),","].join(""));i.getAttribute("cu")||i.setAttribute("cu",k);j=i.getElementsByTagName("player");if(j.length>0){j=j[0].getElementsByTagName("item");var l;for(m=j.length-1;m>-1;m--){(l=j[m].getAttribute("src"))&&l.indexOf("http://")==-1&&j[m].setAttribute("src",n+l);(l=j[m].getAttribute("cp"))&&l.indexOf("http://")==-1&&j[m].setAttribute("cp",n+l)}}j=i.getElementsByTagName("ext");
if(j.length>0){j=j[0].getElementsByTagName("param");for(var r=j.length-1;r>-1;r--){m=j[r].getAttribute("name");l=j[r].getAttribute("value");m=="next"&&l&&l.indexOf("http://")==-1&&j[r].setAttribute("value",n+l)}}h="TagName=ac_tag86_"+this.aid+"&cfg="+encodeURIComponent((i.xml||(new XMLSerializer).serializeToString(i)).replace(/@_@/g,"&"))}h||(this.inner.innerHTML+='<a href="'+k+'" target="_blank" title=""><img src="http://static.acs86.com/transparent.gif" alt="" border="0" style="position:absolute;cursor:pointer;left:0;top:0;width:'+
b+"px;height:"+d+'px;"  /></a>');this.inner.innerHTML+=window.ActiveXObject?'<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=9,0,0,0" id="'+e+'" name="'+e+'" border="0" width="'+b+'" height="'+d+'"><param name="movie" value="'+a+'" /><param name="quality" value="high" /><param name="allowScriptAccess" value="always" /><param name="menu" value="false" /><param name="allowFullScreen" value="true" /><param name="wmode" value="'+
f+'" /><param name="flashvars" value="'+h+'" /></objcet>':'<embed  id="'+e+'" name="'+e+'" width="'+b+'" height="'+d+'" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" allowScriptAccess="always" allowFullScreen="true" quality="high" menu="false" wmode="'+f+'" flashvars="'+h+'" src="'+a+'" />'}else{a=a.indexOf("http://")>-1?a:n+a;this.inner.innerHTML='<iframe id="'+e+'" src="'+a+'" width="'+b+'" height="'+d+'" frameborder="0" scrolling="no" allowTransparency="true" />'}}else{var s=
document.createDocumentFragment();n=/<script\s*(.*?)>(.*?)<\/script>/gi;var t="";i="";i=a.replace(/<link\s*(.*?)\/>/gi,function(o,p){p=p.toLowerCase();if(p.indexOf("text/css")>-1&&p.indexOf("href=")>-1&&p.indexOf("rel=")>-1){var q=document.createElement("link");q.type="text/css";var u=/\s*href=(\'|\")(.*?)(\1)/.exec(p);q.href=u[2];q.rel="Stylesheet";document.getElementsByTagName("head")[0].appendChild(q)}return""});i=a.replace(n,function(o){t+=o;return""});i=a.replace(/<style\s*.*>([^<]*)<\/style>/gi,
function(o,p){var q=document.createElement("style");q.type="text/css";if(window.ActiveXObject)return'<span style="display:none;">&nbsp;</span>'+o;else q.appendChild(document.createTextNode(p));document.getElementsByTagName("head")[0].appendChild(q);return""});this.inner.innerHTML=i;t.replace(n,function(o,p,q){o=document.createElement("script");o.type="text/javascript";if(p.indexOf("src")!=-1){p=/\s*src=(\'|\")(.*?)(\1)/i.exec(p);o.src=p[2]}else if(q)if(window.ActiveXObject)o.text=q;else o.appendChild(document.createTextNode(q));
s.appendChild(o)});s.hasChildNodes()&&this.append(s)}},F:function(a){var b=this,d=b.apm,e=a[0],f=a[1],h=a[2],k=a[6];b.activeOuter=a[3];b.x=a[4];b.y=a[5];b.idx=k;b.fxd=f;b.blk=e;b.ovf=h;a=b.holder||{};k=b.outer.style;var i=a.style,n=c.sfp;b.position&&c.removeEvent(window,"resize",b.position);if(b.interval){clearInterval(b.interval);b.interval=null}if(d)b.info("anchid",b.info("anchid")||a.id);if(e==1&&!d){k.position="relative";k.marginLeft=k.marginTop=0}else if(e==1&&d){i.position="static";i.marginTop=
0;i.marginLeft="auto";i.marginRight="auto"}else if(e==0)if(n&&f==1)k.position="fixed";if(h==1)k.overflow="visible";else if(h==0)k.overflow="hidden";if(e!=1)k.zIndex=2147483647;if(d||!e){b.position=function(){b.setPosition.call(b)};c.addEvent(window,"resize",b.position);b.interval=setTimeout(function(){b.setPosition.call(b);b.interval=setTimeout(arguments.callee,100)},100)}b.isHidden&&b.show();b.position&&b.position()},open:function(a){var b,d=0;if(a){b=document.createElement("a");if(window.ActiveXObject){b.style.display=
"none";b.href=a;b.title="";b.target="_blank";document.body.insertBefore(b,document.body.firstChild);b.click();document.body.removeChild(b);d=1}else if(/firefox/.test(g)||!/chrome/.test(g)&&/safari/.test(g))if(window.open(a))d=1}return d},close:function(){var a=this.outer;if(this.interval){clearInterval(this.interval);this.interval=null}a&&a.parentNode&&a.parentNode.removeChild(a)},setSize:function(a,b,d,e,f){var h=this.middler,k=this.outer,i=[0,0];a=parseInt(a,10);b=parseInt(b,10);e=e||0;f=f||0;if(d&&
d.indexOf("_")!=-1&&this.blk){d=d.toLowerCase().split("_");i[0]=d[0]==="right"?parseInt(k.style.width,10)-a+e:e;i[1]=d[1]==="bottom"?parseInt(k.style.height,10)-b+f:f;h.style.marginLeft=i[0]+"px";h.style.marginTop=i[1]+"px"}this.activeOuter&&this.setOuterSize(a,b);this.setMiddlerSize(a,b);this.setInnerSize(a,b);this.setCreativeSize(a,b)},setOuterSize:function(a,b){this.blk==0&&this.position&&c.css(this.outer,{marginLeft:"-10000px",marginTop:"-10000px"});c.css(this.outer,{width:a+"px",height:b+"px"});
this.position&&this.position()},setMiddlerSize:function(a,b){c.css(this.middler,{width:a+"px",height:b+"px"})},setInnerSize:function(a,b){c.css(this.inner,{width:a+"px",height:b+"px"})},setCreativeSize:function(a,b){var d=c.$(this.creative.id);d.width=a;d.height=b},setZIndex:function(a){this.outer.style.zIndex=a},setParentNodeZIndex:function(a){this.outer.parentNode.style.zIndex=a},setPosition:function(){var a=this.outer.style,b=c.getClient("width"),d=c.getClient("height"),e=parseInt(a.width,10),
f=parseInt(a.height,10),h=c.sfp,k=this.apm,i=0,n=0,j=0,m=0;if(this.info("anchid")){var l=c.getPosition(this.info("anchid"));this.x=l.left;this.y=l.top}else if(this.info("coors")){l=this.info("coors")[this.state-1];this.x=l[0];this.y=l[1];this.sx=l[2];this.sy=l[3];this.cx=l[4];this.cy=l[5]}j=this.x;m=this.y;var r=this.sx||0;l=this.sy||0;var s=this.cx||0,t=this.cy||0,o=this.fxd;if(o===1&&!h){i=c.getScroll("left");n=c.getScroll("top")}if((k||o===1&&!h)&&c.css(document.body,"position")!="static"){if(!c.pal0t0){c.pal0t0=
document.createElement("div");c.pal0t0.style.cssText="position:absolute;left:0;top:0";document.body.insertBefore(c.pal0t0,document.body.firstChild)}i-=c.getPosition(c.pal0t0).left;n-=c.getPosition(c.pal0t0).top}j=String(j).indexOf("%")!=-1?Math.floor((b-parseInt(e,10))*parseInt(j,10)/100)+i:String(j).indexOf("#")!=-1?Math.floor(b/2)+parseInt(j,10)+i:parseInt(j,10)+i;j+=String(r).indexOf("%")!=-1?Math.floor(b*parseInt(r,10)/100):parseInt(r,10);j+=String(s).indexOf("%")!=-1?Math.floor(e*parseInt(s,
10)/100):parseInt(s,10);m=String(m).indexOf("%")!=-1?Math.floor((d-parseInt(f,10))*parseInt(m,10)/100)+n:String(m).indexOf("#")!=-1?Math.floor(d/2)+parseInt(m,10)+n:parseInt(m,10)+n;m+=String(l).indexOf("%")!=-1?Math.floor(d*parseInt(l,10)/100):parseInt(l,10);m+=String(t).indexOf("%")!=-1?Math.floor(f*parseInt(t,10)/100):parseInt(t,10);a.marginLeft=j+"px";a.marginTop=m+"px"},info:function(a,b){if(typeof ac_info_ware=="undefined")ac_info_ware={};ac_info_ware[this.aid]||(ac_info_ware[this.aid]={});
if(typeof b!="undefined")if(b===null)delete ac_info_ware[this.aid][a];else ac_info_ware[this.aid][a]=b;return ac_info_ware[this.aid][a]},ex:function(a,b){this.info("ex")||this.info("ex",{});if(typeof b!="undefined")if(b===null)delete ac_info_ware[this.aid].ex[a];else ac_info_ware[this.aid].ex[a]=b;return ac_info_ware[this.aid].ex[a]},getAdSpaceId:function(){return this.aid},getMediaBuyId:function(){return this.mid},getCreativeId:function(){return this.cid},getCityId:function(){if(typeof _ADCHINA_CITY_ID_!=
"undefined")return _ADCHINA_CITY_ID_;return 0},getPreviousUrl:function(){var a="";try{a=top.document.referrer}catch(b){try{a=document.referrer}catch(d){}}return encodeURIComponent(a)},getCurrentUrl:function(){var a="";try{a=top.document.location}catch(b){try{a=document.location}catch(d){}}return encodeURIComponent(a)},g:function(){ac_info_ware["$tag_"+this.aid].g(null,1)},position:null};c.prototype.init.prototype=c.prototype;c.prototype.init2.prototype=c.prototype;_AC_AJS_VERSION_="0.0.8.1"}})();
(function(){if(!window._acBG){window._acBG=function(g,c){var a={staticUrl:"static.acs86.com/",serverUrl:"acs86.com/",sec:/^https/i.test(window.location.protocol)?"https":"http",loaded:0,rlist:[]},b={p:[0,0],r:3,a:0,c:"transparent"};this.bgcf={};this.padcf={};this.apply(this,a);this.apply(this.bgcf,g,{p:[1,0],r:0,a:0,c:"transparent"});c?this.apply(this.padcf,c,b):this.apply(this.padcf,b);this.initConfig();this.init()};window._acBG.prototype={initConfig:function(){var g=this,c=function(e){if(e.indexOf("http")==
-1){var f="";if(e.indexOf("/")!=0)f="/";e=g.sec+"://"+g.staticUrl+f+e}return e},a=function(e){return{"0":"left","1":"center","2":"right"}[e[0]]+" "+{"0":"top","1":"center","2":"bottom"}[e[1]]},b=function(e){return{"0":"no-repeat","1":"repeat","2":"repeat-x","3":"repeat-y"}[e]},d=function(e){if(e!="transparent"&&e.indexOf("#")==-1)e="#"+e;return e};this.bgcf.i=c(this.bgcf.i);if(this.padcf.i)this.padcf.i=c(this.padcf.i);this.bgcf.p=a(this.bgcf.p);this.padcf.p=a(this.padcf.p);this.bgcf.r=b(this.bgcf.r);
this.padcf.r=b(this.padcf.r);this.bgcf.a={"0":"fixed","1":"scroll"}[this.bgcf.a];this.padcf.a={"0":"fixed","1":"scroll"}[this.padcf.a];this.bgcf.c=d(this.bgcf.c);this.padcf.c=d(this.padcf.c)},init:function(){this.load(function(){this.loaded=1;this.setBg();this.sendReport();this.report=function(g,c,a){_acG.send(this.bdUrl(g,c,a))}})},load:function(g){var c=this,a=new Image;a.onload=function(){g&&g.call(c);a=null};a.src=this.bgcf.i},setBg:function(){if(this.padcf.i)if(window.ActiveXObject&&document.compatMode==
"BackCompat")this.setElBg(document.body,this.bgcf);else{var g=this.getStyle(document.body,"width"),c=0,a=document.documentElement.clientWidth;if(isNaN(parseInt(g))){this.setElBg(document.body,this.bgcf);this.setElBg(document.documentElement,this.padcf)}else{c=parseInt(g);g=this.getStyle(document.body,"paddingRight");var b=this.getStyle(document.body,"paddingLeft"),d=this.getStyle(document.body,"marginLeft"),e=this.getStyle(document.body,"marginRight");isNaN(parseInt(g))||(c+=parseInt(g));isNaN(parseInt(b))||
(c+=parseInt(b));isNaN(parseInt(d))||(c+=parseInt(d));isNaN(parseInt(e))||(c+=parseInt(e));if(c<a){this.setElBg(document.documentElement,this.bgcf);this.clearBodyBg()}else{this.setElBg(document.body,this.bgcf);this.setElBg(document.documentElement,this.padcf)}}}else{if(this.padcf.c!="transparent")this.bgcf.c=this.padcf.c;if(window.ActiveXObject&&document.compatMode=="BackCompat")this.setElBg(document.body,this.bgcf);else{this.setElBg(document.documentElement,this.bgcf);this.clearBodyBg()}}},setElBg:function(g,
c){g.style.backgroundImage="url("+c.i+")";g.style.backgroundPosition=c.p;g.style.backgroundRepeat=c.r;g.style.backgroundAttachment=c.a;g.style.backgroundColor=c.c},clearBodyBg:function(){document.body.style.background="none"},report:function(g,c,a){this.rlist.push(this.bdUrl(g,c,a))},bdUrl:function(g,c,a){return(g+"").indexOf("http")!=-1?g:[this.sec,"://"+this.serverUrl+"v.htm?pv=1&sp=",[g,c,a,2,0,0,0].join(",")].join("")},sendReport:function(){for(var g=0,c;c=this.rlist[g];++g)_acG.send(c)},getStyle:function(g,
c){var a=document.defaultView;return a&&a.getComputedStyle?a.getComputedStyle(g,null)[c]:g.currentStyle[c]},apply:function(g,c,a){a&&this.apply(g,a);if(g&&c&&typeof c=="object")for(var b in c)g[b]=c[b];return g},constructor:_acBG}}})();
