/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 *  android.view.View
 *  android.webkit.WebView
 *  android.webkit.WebViewClient
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Collections
 *  java.util.HashMap
 *  java.util.Map
 */
package com.flurry.android;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.flurry.android.AdUnit;
import com.flurry.android.FlurryFullscreenTakeoverActivity;
import com.flurry.android.aj;
import com.flurry.android.bb;
import com.flurry.android.bc;
import com.flurry.android.y;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

final class aq
extends WebViewClient {
    private /* synthetic */ aj a;

    /* synthetic */ aq(aj aj2) {
        super(aj2, (byte)0);
    }

    private aq(aj aj2, byte by) {
        this.a = aj2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void onPageFinished(WebView webView, String string) {
        aj.a(this.a).loadUrl("javascript:var Hogan={};(function(a,b){function i(a){return String(a===null||a===undefined?\"\":a)}function j(a){return a=i(a),h.test(a)?a.replace(c,\"&amp;\").replace(d,\"&lt;\").replace(e,\"&gt;\").replace(f,\"&#39;\").replace(g,\"&quot;\"):a}a.Template=function(a,c,d,e){this.r=a||this.r,this.c=d,this.options=e,this.text=c||\"\",this.buf=b?[]:\"\"},a.Template.prototype={r:function(a,b,c){return\"\"},v:j,t:i,render:function(b,c,d){return this.ri([b],c||{},d)},ri:function(a,b,c){return this.r(a,b,c)},rp:function(a,b,c,d){var e=c[a];return e?(this.c&&typeof e==\"string\"&&(e=this.c.compile(e,this.options)),e.ri(b,c,d)):\"\"},rs:function(a,b,c){var d=a[a.length-1];if(!k(d)){c(a,b,this);return}for(var e=0;e<d.length;e++)a.push(d[e]),c(a,b,this),a.pop()},s:function(a,b,c,d,e,f,g){var h;return k(a)&&a.length===0?!1:(typeof a==\"function\"&&(a=this.ls(a,b,c,d,e,f,g)),h=a===\"\"||!!a,!d&&h&&b&&b.push(typeof a==\"object\"?a:b[b.length-1]),h)},d:function(a,b,c,d){var e=a.split(\".\"),f=this.f(e[0],b,c,d),g=null;if(a===\".\"&&k(b[b.length-2]))return b[b.length-1];for(var h=1;h<e.length;h++)f&&typeof f==\"object\"&&e[h]in f?(g=f,f=f[e[h]]):f=\"\";return d&&!f?!1:(!d&&typeof f==\"function\"&&(b.push(g),f=this.lv(f,b,c),b.pop()),f)},f:function(a,b,c,d){var e=!1,f=null,g=!1;for(var h=b.length-1;h>=0;h--){f=b[h];if(f&&typeof f==\"object\"&&a in f){e=f[a],g=!0;break}}return g?(!d&&typeof e==\"function\"&&(e=this.lv(e,b,c)),e):d?!1:\"\"},ho:function(a,b,c,d,e){var f=this.c,g=this.options;g.delimiters=e;var h=a.call(b,d,function(a){return f.compile(a,g).render(b,c)});return this.b(f.compile(h.toString(),g).render(b,c)),!1},b:b?function(a){this.buf.push(a)}:function(a){this.buf+=a},fl:b?function(){var a=this.buf.join(\"\");return this.buf=[],a}:function(){var a=this.buf;return this.buf=\"\",a},ls:function(a,b,c,d,e,f,g){var h=b[b.length-1],i=null;if(!d&&this.c&&a.length>0)return this.ho(a,h,c,this.text.substring(e,f),g);i=a.call(h);if(typeof i==\"function\"){if(d)return!0;if(this.c)return this.ho(i,h,c,this.text.substring(e,f),g)}return i},lv:function(a,b,c){var d=b[b.length-1],e=a.call(d);return typeof e==\"function\"&&(e=e.call(d)),e=i(e),this.c&&~e.indexOf(\"{{\")?this.c.compile(e,this.options).render(d,c):e}};var c=/&/g,d=/</g,e=/>/g,f=/\\'/g,g=/\\\"/g,h=/[&<>\\\"\\']/,k=Array.isArray||function(a){return Object.prototype.toString.call(a)===\"[object Array]\"}})(typeof exports!=\"undefined\"?exports:Hogan),function(a){function h(a){a.n.substr(a.n.length-1)===\"}\"&&(a.n=a.n.substring(0,a.n.length-1))}function i(a){return a.trim?a.trim():a.replace(/^\\s*|\\s*$/g,\"\")}function j(a,b,c){if(b.charAt(c)!=a.charAt(0))return!1;for(var d=1,e=a.length;d<e;d++)if(b.charAt(c+d)!=a.charAt(d))return!1;return!0}function k(a,b,c,d){var e=[],f=null,g=null;while(a.length>0){g=a.shift();if(g.tag==\"#\"||g.tag==\"^\"||l(g,d))c.push(g),g.nodes=k(a,g.tag,c,d),e.push(g);else{if(g.tag==\"/\"){if(c.length===0)throw new Error(\"Closing tag without opener: /\"+g.n);f=c.pop();if(g.n!=f.n&&!m(g.n,f.n,d))throw new Error(\"Nesting error: \"+f.n+\" vs. \"+g.n);return f.end=g.i,e}e.push(g)}}if(c.length>0)throw new Error(\"missing closing tag: \"+c.pop().n);return e}function l(a,b){for(var c=0,d=b.length;c<d;c++)if(b[c].o==a.n)return a.tag=\"#\",!0}function m(a,b,c){for(var d=0,e=c.length;d<e;d++)if(c[d].c==a&&c[d].o==b)return!0}function n(a){return'var _=this;_.b(i=i||\"\");'+q(a)+\"return _.fl();\"}function o(a){return a.replace(f,\"\\\\\\\\\").replace(c,'\\\\\"').replace(d,\"\\\\n\").replace(e,\"\\\\r\")}function p(a){return~a.indexOf(\".\")?\"d\":\"f\"}function q(a){var b=\"\";for(var c=0,d=a.length;c<d;c++){var e=a[c].tag;e==\"#\"?b+=r(a[c].nodes,a[c].n,p(a[c].n),a[c].i,a[c].end,a[c].otag+\" \"+a[c].ctag):e==\"^\"?b+=s(a[c].nodes,a[c].n,p(a[c].n)):e==\"<\"||e==\">\"?b+=t(a[c]):e==\"{\"||e==\"&\"?b+=u(a[c].n,p(a[c].n)):e==\"\\n\"?b+=w('\"\\\\n\"'+(a.length-1==c?\"\":\" + i\")):e==\"_v\"?b+=v(a[c].n,p(a[c].n)):e===undefined&&(b+=w('\"'+o(a[c])+'\"'))}return b}function r(a,b,c,d,e,f){return\"if(_.s(_.\"+c+'(\"'+o(b)+'\",c,p,1),'+\"c,p,0,\"+d+\",\"+e+',\"'+f+'\")){'+\"_.rs(c,p,\"+\"function(c,p,_){\"+q(a)+\"});c.pop();}\"}function s(a,b,c){return\"if(!_.s(_.\"+c+'(\"'+o(b)+'\",c,p,1),c,p,1,0,0,\"\")){'+q(a)+\"};\"}function t(a){return'_.b(_.rp(\"'+o(a.n)+'\",c,p,\"'+(a.indent||\"\")+'\"));'}function u(a,b){return\"_.b(_.t(_.\"+b+'(\"'+o(a)+'\",c,p,0)));'}function v(a,b){return\"_.b(_.v(_.\"+b+'(\"'+o(a)+'\",c,p,0)));'}function w(a){return\"_.b(\"+a+\");\"}var b=/\\S/,c=/\\\"/g,d=/\\n/g,e=/\\r/g,f=/\\\\/g,g={\"#\":1,\"^\":2,\"/\":3,\"!\":4,\">\":5,\"<\":6,\"=\":7,_v:8,\"{\":9,\"&\":10};a.scan=function(c,d){function w(){p.length>0&&(q.push(new String(p)),p=\"\")}function x(){var a=!0;for(var c=t;c<q.length;c++){a=q[c].tag&&g[q[c].tag]<g._v||!q[c].tag&&q[c].match(b)===null;if(!a)return!1}return a}function y(a,b){w();if(a&&x())for(var c=t,d;c<q.length;c++)q[c].tag||((d=q[c+1])&&d.tag==\">\"&&(d.indent=q[c].toString()),q.splice(c,1));else b||q.push({tag:\"\\n\"});r=!1,t=q.length}function z(a,b){var c=\"=\"+v,d=a.indexOf(c,b),e=i(a.substring(a.indexOf(\"=\",b)+1,d)).split(\" \");return u=e[0],v=e[1],d+c.length-1}var e=c.length,f=0,k=1,l=2,m=f,n=null,o=null,p=\"\",q=[],r=!1,s=0,t=0,u=\"{{\",v=\"}}\";d&&(d=d.split(\" \"),u=d[0],v=d[1]);for(s=0;s<e;s++)m==f?j(u,c,s)?(--s,w(),m=k):c.charAt(s)==\"\\n\"?y(r):p+=c.charAt(s):m==k?(s+=u.length-1,o=g[c.charAt(s+1)],n=o?c.charAt(s+1):\"_v\",n==\"=\"?(s=z(c,s),m=f):(o&&s++,m=l),r=s):j(v,c,s)?(q.push({tag:n,n:i(p),otag:u,ctag:v,i:n==\"/\"?r-v.length:s+u.length}),p=\"\",s+=v.length-1,m=f,n==\"{\"&&(v==\"}}\"?s++:h(q[q.length-1]))):p+=c.charAt(s);return y(r,!0),q},a.generate=function(b,c,d){return d.asString?\"function(c,p,i){\"+b+\";}\":new a.Template(new Function(\"c\",\"p\",\"i\",b),c,a,d)},a.parse=function(a,b,c){return c=c||{},k(a,\"\",[],c.sectionTags||[])},a.cache={},a.compile=function(a,b){b=b||{};var c=a+\"||\"+!!b.asString,d=this.cache[c];return d?d:(d=this.generate(n(this.parse(this.scan(a,b.delimiters),a,b)),a,b),this.cache[c]=d)}}(typeof exports!=\"undefined\"?exports:Hogan)");
        if (aj.b(this.a)) {
            aj.a(this.a).loadUrl("javascript:(function() {       var flurryadapter = window.flurryadapter = {};       flurryadapter.flurryCallQueue = [ ];       flurryadapter.flurryCallInProgress = false;       flurryadapter.callComplete = function( cmd ) {          if ( this.flurryCallQueue.length == 0 ) {             this.flurryCallInProgress = false;             return;          }           var adapterCall = this.flurryCallQueue.pop();           this.executeNativeCall(adapterCall);          return \"OK\";       };       flurryadapter.executeCall = function( command ) {          var adapterCall = \"flurry://flurrycall?event=\" + command;          var value;          for ( var i = 1; i < arguments.length; i += 2 ) {             value = arguments[i + 1];             if ( value == null ) {                continue;             }             adapterCall += \"&\" + arguments[i] + \"=\" + escape( value );          }          if ( this.flurryCallInProgress ) {             this.flurryCallQueue.push( adapterCall );          }          else {            this.executeNativeCall(adapterCall);          }       };     flurryadapter.executeNativeCall = function( adapterCall ) {         if ( adapterCall.length == 0 ) {            return;         }         this.flurryCallInProgress = true;         window.location = adapterCall;     };    window.flurryAdapterAvailable = true;    if (typeof FlurryAdapterReady == 'function') {        FlurryAdapterReady();    } })();");
            return;
        } else {
            String string2 = y.f(this.a.b());
            aj.a(this.a).loadUrl("javascript:var content='" + string2 + "';var compiled=Hogan.compile(document.body.innerHTML);var rendered=compiled.render(JSON.parse(content));document.body.innerHTML=rendered;");
            aj.a(this.a, "rendered", Collections.emptyMap(), aj.c(this.a), aj.d(this.a), aj.e(this.a), 0);
            if (this.a.getChildCount() >= 1) return;
            {
                this.a.addView((View)aj.a(this.a));
                return;
            }
        }
    }

    public final void onReceivedError(WebView webView, int n2, String string, String string2) {
        aj.a(this.a, "renderFailed", Collections.emptyMap(), aj.c(this.a), aj.d(this.a), aj.e(this.a), 0);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean shouldOverrideUrlLoading(WebView webView, String string) {
        block9 : {
            HashMap hashMap;
            String string2;
            block11 : {
                block10 : {
                    Uri uri = Uri.parse((String)string);
                    if (uri.getScheme() == null || !uri.getScheme().equals((Object)"flurry")) break block9;
                    string2 = uri.getQueryParameter("event");
                    if (string2 == null) break block10;
                    String string3 = uri.getQuery();
                    hashMap = new HashMap();
                    if (string3 != null) {
                        String[] arrstring = string3.split("&");
                        int n2 = arrstring.length;
                        for (int i2 = 0; i2 < n2; ++i2) {
                            String[] arrstring2 = arrstring[i2].split("=");
                            if (arrstring2[0].equals((Object)"event")) continue;
                            hashMap.put((Object)arrstring2[0], (Object)arrstring2[1]);
                        }
                    }
                    if (!hashMap.containsKey((Object)"guid")) break block11;
                    AdUnit adUnit = aj.a(this.a, (String)hashMap.get((Object)"guid"));
                    bb bb2 = aj.b(this.a, (String)hashMap.get((Object)"guid"));
                    aj.a(this.a, string2, (Map)hashMap, adUnit, bb2, 0, 0);
                }
                do {
                    return true;
                    break;
                } while (true);
            }
            aj.a(this.a, string2, (Map)hashMap, aj.c(this.a), aj.d(this.a), aj.e(this.a), 0);
            return true;
        }
        if (this.a.c().equals((Object)"takeover")) {
            return false;
        }
        Intent intent = new Intent();
        intent.setClass(aj.g(this.a), FlurryFullscreenTakeoverActivity.class);
        intent.putExtra("url", string);
        if (y.a(aj.g(this.a), intent)) {
            aj.g(this.a).startActivity(intent);
            do {
                return true;
                break;
            } while (true);
        }
        bc.b(aj.f(this.a), "Unable to launch FlurryFullscreenTakeoverActivity, falling back to browser. Fix by declaring this Activity in your AndroidManifest.xml");
        y.a(aj.g(this.a), string);
        return true;
    }
}

