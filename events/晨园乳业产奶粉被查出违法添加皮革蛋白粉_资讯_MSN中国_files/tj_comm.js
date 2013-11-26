function ynstat_ranking()
{
var refer=null;
if(document.referrer) refer=document.referrer;
else if(window.opener) refer=window.opener.location;
else refer="MSN Window";
document.write("<script type='text/javascript' src='http://ranking.ynet.com:8080/tjtool.js?ref="+refer+"'></script>");
document.write("<script language='javascript' src='http://msn.ent.ynet.com/gall/10171.js?0,1'></script>");
}
ynstat_ranking();