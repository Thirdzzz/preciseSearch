/*! Copyright 2011 Baidu Inc. All Rights Reserved. */
(function(){var c=!0,C="1",f="0",F=document,s=F.body,Z=F.compatMode!=="CSS1Compat",aG=Z?s:F.documentElement,y=null,o,n=function(aR,E,aS){aR.setAttribute(E,aS)},ak=!1,aB=window.BAIDU_CLB_SLOTS_MAP,Y=window.BAIDU_DAN_config||{},aL="BAIDU_DAN_readySlotId",aC=c?"http://gmvl.baidu.com/dan/sp.html":"",aI="http://img.baidu.com/adm/video-player.swf",S="http://img.baidu.com/adm/gm-logo-100x100.png",R="BAIDU_DAN_materialCallback",av="BAIDU_DAN_showAd",aO=16,g=100,i=100,aP=2147483647,p=5000,aa=Object.prototype.hasOwnProperty,ab="data-baidu-dan-id",al="data-baidu-dan-type",af="data-baidu-dan-position-fix",ad="data-baidu-dan-tick",H="data-baidu-dan-count",aw="data-baidu-dan-alp",k="data-baidu-dan-alr",ap="data-baidu-dan-alrs",r="data-baidu-dan-alm",aF="data-baidu-dan-allc",X="data-baidu-dan-alsc",z="data-baidu-dan-als",az="data-baidu-dan-player-state",an="data-baidu-dan-player-toggle-state";
(function(){var aR=F.createElement("div"),E=F.createElement("div");aR.style.position="absolute";aR.style.top="200px";E.style.position="fixed";E.style.top="100px";aR.appendChild(E);s.appendChild(aR);E.getBoundingClientRect&&E.getBoundingClientRect().top==aR.getBoundingClientRect().top&&(y={});s.removeChild(aR)})();(function(){var E=F.createElement("object");"classid" in E&&(ak=!0,E.setAttribute("test","test"),E.getAttribute("test")||(n=function(aR,aT,aS){aR[aT]=aS}))})();(function(){var E=F.createElement("div");E.innerHTML='<div style="opacity:.25;"></div>';o=E.firstChild.style.opacity=="0.25"})();function M(aR){var E=h(aR);if(E.materialType==4){F.write(E.htmlCode)}else{var aS=aD[E.productType];aS&&(E.direct?aS(E):j(function(){aS(E)}))}}window[av]=M;function ag(E){return encodeURIComponent(E)
}function u(aR){var E=aR.id,aV=aR.width,aU=aR.height,aT=t(E,aV,aU),aS=D(aR,"video",aT);aM(aS,E,aV,aU);aj(aT,2);s.appendChild(aS);ay(aT,aR);y&&at()}function ah(aR){var E=aR.width,aW=aR.height,aV=t(aR.id,E,aW),aU=D(aR,"top-down",aV),aT=aU.style,aS=F.body;aT.height="0";aT.margin="0 auto";aT.width=E+"px";aS.insertBefore(aU,aS.firstChild);ay(aV,aR);V(aU,"height",0,aW);setTimeout(function(){V(aU,"height",aW,0)},aR.maxImpressionTime)}function d(a1){var a0=a1.id,aZ=a1.width,aY=a1.height,aX=F.createElement("div"),aW=t(a0,aZ,aY),aV=aK("minimize","close"),aU=F.createElement("div"),aT=au(a0),aR=aK("close"),E=D(a1,"barrier",[aX,aU]),aS=E.style;aU.style.display="none";aX.appendChild(aW);aX.appendChild(aV);aU.appendChild(aR);aU.appendChild(aT);aS.width=aZ+"px";aS.height=aY+aO+"px";aS.zIndex=aP;Q(E,a0,aZ,aY+aO);
aj(aX,1);aj(aU,3);s.appendChild(E);ay(aW,a1);aq(aT,a1.collapseType,a1.collapseSrc);y&&at();e(E,a1.maxImpressionTime)}function K(aY){var aX=aY.id,aW=aY.width,aV=aY.height,aU=aK("close"),aT=t(aX,aW,aV),aS=au(aX,!1),aR=D(aY,"float",aS),E=aR.style;aj(aS,2);aM(aR,aX,aW,aV);E.width=aW+"px";E.height=aV+aO+"px";E.zIndex=aP;s.appendChild(aR);aS.appendChild(aU);aS.appendChild(aT);ay(aT,aY);y&&at()}function x(aR){var E=aR.id,aV=aR.width,aU=aR.height,aT=t(E,aV,aU),aS=D(aR,"float",aT);aR.flashHosted=!0;aj(aT,2);aM(aS,E,aV,aU);s.appendChild(aS);ay(aT,aR);y&&at()}function aH(aR){var E=aR.id,aU=document.getElementsByTagName("script"),aU=aU[aU.length-1],aT=aU.parentNode,E=t(E,aR.width,aR.height),aS=D(aR,"static",E);aT.insertBefore(aS,aU.nextSibling);ay(E,aR)}var aD=[u,x,d,ah,aH,aH,u,K];function l(E){E=ac(E);
W(E);E.style.display="none";setTimeout(function(){E.parentNode.removeChild(E)},0);return !1}function t(aR,E,aU){var aT=F.createElement("ins"),aS=aT.style;aT.id="baidu_dan_"+aR;aS.display="block";aS.width=E+"px";aS.height=aU+"px";aS.position="relative";aS.overflow="hidden";return aT}function au(aR,E){var aS=F.createElement("ins");aS.id="baidu_dan_collapsed_"+aR;aS.style.display="block";E!==!1&&P(aS,"mouseover",G);return aS}function D(aR,E,aX){var aX=aX instanceof Array?aX:[aX],aW=aR.id,aV=F.createElement("ins"),aU=aV.style,aT=0,aS=aX.length;aV.id="baidu_dan_wrapper_"+aW;aV.setAttribute(ab,aW);aV.setAttribute(al,E);aV.setAttribute(an,aR.initialState);aU.display="block";aU.overflow="hidden";aU.position="relative";aU.textDecoration="none";aU.zIndex=aP;if(aR.slotWidth){aU.width=aR.slotWidth+"px"
}if(aR.slotHeight){aU.height=aR.slotHeight+"px"}for(;aT<aS;aT++){aV.appendChild(aX[aT])}return aV}function V(a1,a0,aZ,aY,aX,aW){function aV(){aR--;aS=aR?aS+E:aY;aU[a0]=aS+"px";aR&&setTimeout(aV,aT)}var aU=a1.style,aT=aW||20,aR=(aX||800)/aT,E=Math.round((aY-aZ)/aR),aS=aZ;aU[a0]=aZ+"px";setTimeout(aV,aT)}function aq(aR,E,aS){E===0&&aN(aR,{src:aS,width:g,height:i})}function ay(aR,E){var aS=B[E.materialType];aS&&aS(aR,E)}function aQ(aR,E){var aT={id:"baidu_dan_player_"+E.id,width:E.width,height:E.height,src:E.playerUrl||aI},aS=["player_id="+ag(E.id),"play_url="+ag(E.src),"target_url="+ag(E.targetUrl),"target_window="+ao[E.targetWindow],"rcv_url="+E.rcvUrl,"effect_time="+(E.maxImpressionTime/1000).toFixed(),"collapse_src="+ag(E.collapseSrc),"initial_state="+E.initialState,"query="+ag(E.rcvQuery),"o_mute="+(Y.videoMute?"1":"0"),"o_volume="+(typeof Y.videoVolume=="number"?Y.videoVolume:"20")];
aN(aR,aT,aS.join("&"));E.initialState==C&&q(aR)}function aN(aR,E,aS){E.flashHosted&&(aS="id="+E.id+"&target_url="+ag(E.targetUrl)+"&target_window="+ao[E.targetWindow]);aR.innerHTML=ak?'<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" name="baidu_dan_player" '+(E.id?'id="baidu_dan_flash_'+E.id+'" ':"")+'width="'+E.width+'" height="'+E.height+'" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,0,0"><param name="allowScriptAccess" value="always" /><param name="quality" value="high" /><param name="wmode" value="transparent" /><param name="movie" value="'+E.src+'" />'+(aS?'<param name="flashvars" value="'+aS+'" />':"")+"</object>":'<embed wmode="transparent" src="'+E.src+'" quality="high" name="baidu_dan_player" '+(E.id?'id="'+E.id+'" ':"")+(aS?'flashVars="'+aS+'" ':"")+'width="'+E.width+'" height="'+E.height+'"allowScriptAccess="always" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" />';
E.flashHosted||ar(aR,E.targetUrl,E.targetWindow)}function am(aR,E){var aS=F.createElement("img");aS.width=E.width;aS.height=E.height;aS.src=E.src;aS.style.display="block";aR.appendChild(aS);ar(aR,E.targetUrl,E.targetWindow)}function ar(aR,E,aU){if(E){var aU=ao[aU]||aU,aT=F.createElement("a"),aS=aT.style;aT.href=E;aT.target=aU;aS.position="absolute";aS.zIndex=999;aS.top=0;aS.width="100%";aS.height="2000px";aS.backgroundColor="#fff";o?aS.opacity=0:(aS.filter="alpha(opacity=0)",aS.zoom=1);"outline" in aS?aS.outline="none":aT.hideFocus=!0;aR.insertBefore(aT,aR.firstChild)}}var aA={close:{text:"\u5173\u95ed",width:16,height:16,offsetX:0,offsetY:0,image:"http://img.baidu.com/adm/control.gif",handler:l},minimize:{text:"\u6700\u5c0f\u5316",width:16,height:16,offsetX:-16,offsetY:0,image:"http://img.baidu.com/adm/control.gif",handler:q}};
function aK(aR){var E=0,aW=arguments.length,aV,aU,aT,aS=F.createElement("div");aS.className="baidu-dan-control-bar";for(aS.style.overflow="hidden";E<aW;E++){aV=arguments[E],aV=aA[aV],aU=F.createElement("span"),aT=aU.style,aU.innerHTML=aV.text,aT.width=aV.width+"px",aT.height=aV.height+"px",aT.backgroundImage="url("+aV.image+")",aT.backgroundPosition=aV.offsetX+"px "+aV.offsetY+"px",aT.textIndent="-2000px",aT.overflow="hidden","cssFloat" in aT?aT.cssFloat="right":aT.styleFloat="right",P(aU,"click",aV.handler),aS.insertBefore(aU,aS.firstChild)}return aS}var ao=["_self","_blank"],B=[aQ,aQ,aN,am,null,aQ,aQ],U={barrier:{toMain:function(aR,E,aT,aS){aR.style.height=A(aR).height+aO+"px";if(!y){E=aR.style,E.bottom="auto",E.right="auto"}Q(aR,void 0,aS.width,aS.height)},toCollapsed:function(E){E=E.style;
E.marginTop=0;E.marginLeft=0;E.height=i+aO+"px";E.top="auto";E.left="auto"}},video:{toMain:function(aR,E){var aS=E.getElementsByTagName(ak?"object":"embed")[0];aM(aR);v(aS,"start");return !0},toCollapsed:function(aR,E){var aT=E.getElementsByTagName(ak?"object":"embed")[0],aS=+aT.getAttribute(H)||0;aS++;n(aT,H,aS);v(aT,"stop");return !0}}};function q(aR){var aR=ac(aR),E=aR.getAttribute(al),aV=A(aR),aU=aR.firstChild,aT=aR.lastChild,aS=aR.style;setTimeout(function(){aS.width=g+"px";aS.height=i+"px";aR.setAttribute(an,C);var aW=U[E]&&U[E].toCollapsed(aR,aU,aT,aV);aV.productType==2?ax(aR,aR.getAttribute(ab)):aM(aR,aR.getAttribute(ab));if(!aW&&aU!==aT){aU.style.display="none",aT.style.display="block"}},0);return !1}function G(aR){var aR=ac(aR),E=aR.style,aX=A(aR),aW=aX.width,aV=aX.height,aU=aR.firstChild,aT=aR.lastChild,aS=aR.getAttribute(al);
W(aR);E.width=aW+"px";E.height=aV+"px";aR.setAttribute(an,f);if((!U[aS]||!U[aS].toMain(aR,aU,aT,aX))&&aU!==aT){aT.style.display="none",aU.style.display="block"}y&&(aU.getAttribute(af)||delete y[aX.id],at());aR.getAttribute(ad)&&e(aR,aX.maxImpressionTime);return !1}function e(aR,E){aR.setAttribute(ad,setTimeout(function(){q(aR)},E))}function W(E){(E=+E.getAttribute(ad))&&clearTimeout(E)}var P=window.addEventListener?function(aR,E,aS){aR.addEventListener(E,aS,!1)}:function(aR,E,aS){aR.attachEvent("on"+E,aS)},T=window.removeEventListener?function(aR,E,aS){aR.removeEventListener(E,aS,!1)}:function(aR,E,aS){aR.detachEvent("on"+E,aS)};function A(E){return h(E.getAttribute(ab))}function a(E){return E.product_type==6?C:E.initial_state?E.initial_state:f}function h(aR,E){var aT=E||aB[aR],aS=aT._html;
return{id:aR,slotWidth:aT._w,slotHeight:aT._h,urlParameter:aS.url_parameter,productType:aS.product_type,direct:aS.product_type===4||aS.product_type===5,materialType:aS.material_type,src:aS.src,width:aS.width,height:aS.height,playerUrl:aS.player_url,collapseType:aS.collapse_type,collapseSrc:aS.collapse_src||S,initialState:a(aS),targetUrl:aS.target_url,targetWindow:aS.target_window,htmlCode:aS.html_code,maxImpressionTime:aS.max_impression_time*1000||5000,rcvUrl:aS.rcv_url,rcvQuery:aS.query}}function L(aR){var aR=aR&&aR.style,E=0,aT=0,aS;if(y&&!Z&&(aS=s.style.position||(s.currentStyle?s.currentStyle.position:""),aS==="relative"||aS==="absolute")){E=s.offsetLeft||0,aT=s.offsetTop||0}if(aR){aR.top="0",aR.left="0"}return{width:aG.clientWidth,height:aG.clientHeight,scrollLeft:window.pageXOffset||aG.scrollLeft,scrollTop:window.pageYOffset||aG.scrollTop,bodyOffsetLeft:E,bodyOffsetTop:aT}
}function ax(aR,E,aU,aT){var aS=aR.style;aU?(aS.width=aU+"px",aS.height=aT+"px"):(aU=aR.offsetWidth,aT=aR.offsetHeight);y?(b(aR,aU,aT),E&&(y[E]=aR)):(aS.position="fixed",aS.top="50%",aS.right=0,aS.marginTop=(-(aT/2)).toFixed()+"px")}function aM(aR,E,aU,aT){var aS=aR.style;aU?(aS.width=aU+"px",aS.height=aT+"px"):(aU=aR.offsetWidth,aT=aR.offsetHeight);y?(m(aR,aU,aT),E&&(y[E]=aR)):(aS.position="fixed",aS.bottom=0,aS.right=0)}function Q(aR,E,aT,aS){y?(w(aR,aT,aS),E&&(y[E]=aR)):(aR=aR.style,aR.position="fixed",aR.top="50%",aR.left="50%",aR.marginLeft=(-(aT/2)).toFixed()+"px",aR.marginTop=(-(aS/2)).toFixed()+"px")}function aj(aR,E){y&&n(aR,af,E)}function ac(E){E=E||window.event;"nodeName" in E||(E=E.target||E.srcElement);for(;E&&!E.getAttribute(ab);){E=E.parentNode}return E}function w(aR,E,aT){var E=E||aR.offsetWidth,aT=aT||aR.offsetHeight,aS=aR.style,aR=L(aR);
aS.position="absolute";aS.left=aR.scrollLeft+aR.width/2+"px";aS.top=aR.scrollTop+aR.height/2+"px";aS.marginLeft=(-(E/2)).toFixed()+"px";aS.marginTop=(-(aT/2)).toFixed()+"px"}function m(aR,E,aT){var E=E||aR.offsetWidth,aT=aT||aR.offsetHeight,aS=aR.style,aR=L(aR);aS.position="absolute";aS.left=aR.scrollLeft+aR.width-aR.bodyOffsetLeft-E+"px";aS.top=aR.scrollTop+aR.height-aR.bodyOffsetTop-aT+"px"}function b(aR,E,aT){var E=E||aR.offsetWidth,aT=aT||aR.offsetHeight,aS=aR.style,aR=L(aR);aS.position="absolute";aS.left=aR.scrollLeft+aR.width-E+"px";aS.top=aR.scrollTop+(aR.height-aT)/2+"px"}var ai=[null,w,m,b];function at(){var aR,E,aU=0,aT,aS;for(aR in y){aa.call(y,aR)&&(aU++,E=y[aR],aT=E.lastChild.offsetWidth?E.lastChild:E.firstChild,aS=aT.getAttribute(af),setTimeout(function(){ai[aS](E)},0))}aU==0?(N=!1,T(window,"scroll",at),T(window,"resize",at)):N||(N=!0,P(window,"scroll",at),P(window,"resize",at))
}var N=!1;function ae(aR,E){var aU=F.getElementById("baidu_dan_wrapper_"+aR),aT=ae[E],aS=ak?"object":"embed",aS=aU&&aU.firstChild.getElementsByTagName(aS)[0];J[E]&&aU.getAttribute(al)=="video"&&n(aS,az,J[E]);aU&&aT&&aT(aU,aS,aR)}window[R]=ae;ae.onMinimize=q;ae.onRestore=G;ae.onClose=function(aR,E){aR.getAttribute(al)==="video"&&v(E,"stop");l(aR)};ae.onLoad=function(aR,E){aR.getAttribute(an)===f?v(E,"start"):O(E)};ae.onFinish=function(aR,E){v(E,"stop")};ae.onPause=function(aR,E){v(E,"stop")};ae.onResume=function(aR,E){aR.getAttribute(an)===f?v(E,"start"):O(E)};ae.logMousePause=function(aR,E){var aS=+E.getAttribute(aw)||0;n(E,aw,++aS);O(E)};ae.logMouseReplay=function(aR,E){var aS=+E.getAttribute(k)||0;n(E,k,++aS);O(E)};ae.logMouseRestore=function(aR,E){var aS=+E.getAttribute(ap)||0;n(E,ap,++aS);
O(E)};ae.logMouseMute=function(aR,E){var aS=+E.getAttribute(r)||0;n(E,r,++aS);O(E)};ae.logMouseMinimize=function(aR,E){var aS=+E.getAttribute(aF)||0;n(E,aF,++aS);O(E)};ae.logMouseClose=function(aR,E){var aS=+E.getAttribute(X)||0;n(E,X,++aS);O(E)};ae.logMouseSearch=function(aR,E){var aS=+E.getAttribute(z)||0;n(E,z,++aS);O(E)};var J={onLoad:"playing",onResume:"playing",onPause:"paused",onFinish:"stopped"};function O(a3){var a2=0,a1=ac(a3),a0=A(a1);try{a2=a3.prototype_getTime()||0}catch(aZ){return !1}var aY=+new Date,aX=+a3.getAttribute(H)||0,aW=a0.urlParameter,a0=a0.productType,aV=+a3.getAttribute(aw)||0,aT=+a3.getAttribute(k)||0,aS=+a3.getAttribute(ap)||0,aU=+a3.getAttribute(r)||0,aR=+a3.getAttribute(aF)||0,E=+a3.getAttribute(X)||0,a3=+a3.getAttribute(z)||0,a1=a1.getAttribute(an)||f,a2=aC+"?"+aW+"&tm="+ag(aY)+"&l="+ag(a2)+"&m="+ag(aX)+"&alp="+ag(aV)+"&alr="+ag(aT)+"&alrs="+ag(aS)+"&alm="+ag(aU)+"&allc="+ag(aR)+"&alsc="+ag(E)+"&als="+ag(a3)+"&tgs="+ag(a1)+"&pt="+ag(a0);
aJ(a2);return !0}function v(aR,E){O(aR);switch(E){case"start":clearInterval(+aR.getAttribute(ad));aR.getAttribute(az)=="playing"&&n(aR,ad,setInterval(function(){v(aR)},p));break;case"stop":clearInterval(+aR.getAttribute(ad))}}function aJ(aR){var E=new Image,aS="baidu_dan_log_"+ +new Date;window[aS]=E;E.onload=E.onerror=E.onabort=function(){try{delete window[aS]}catch(aT){window[aS]=void 0}E=null};aR+=aR.indexOf("?")>-1?"&":"?";aR+=".stamp="+Math.random();E.src=aR}var j=function(){function aR(){if(!E){E=!0;for(var aU=0,aV=aT.length;aU<aV;aU++){aT[aU]&&aT[aU]()}aT.length=0}}var E=!1,aT=[],aS=!1;return function(aU){if(E||document.readyState==="complete"){return aU()}aT.push(aU);aS||(aS=!0,F.addEventListener?P(F,"DOMContentLoaded",function(){T(F,"DOMContentLoaded",arguments.callee);aR()}):F.attachEvent&&F.documentElement.doScroll&&function(){if(!E){try{F.documentElement.doScroll("left")
}catch(aV){setTimeout(arguments.callee,50);return}aR()}}(),P(window,"load",aR))}}(),I=window[aL];if(I){M(I);try{delete window[aL]}catch(aE){window[aL]=void 0}}})();