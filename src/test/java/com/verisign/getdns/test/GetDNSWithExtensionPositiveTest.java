package com.verisign.getdns.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

import com.verisign.getdns.ContextOptionName;
import com.verisign.getdns.ExtensionName;
import com.verisign.getdns.GetDNSConstants;
import com.verisign.getdns.GetDNSFactory;
import com.verisign.getdns.GetDNSFutureResult;
import com.verisign.getdns.GetDNSUtil;
import com.verisign.getdns.IGetDNSContext;
import com.verisign.getdns.RRType;

public class GetDNSWithExtensionPositiveTest {

	/**
	 * test with dnssec return status value
	 */
	@Test
	public void testGetDNSWithDnssecStatusExtension() {
		System.out.println("--------DNSSEC_RETURN_STATUS TEST--------------");
		final IGetDNSContext context = GetDNSFactory.create(1);
		try {
			HashMap<ExtensionName, Object> extensions = new HashMap<ExtensionName, Object>();
			extensions.put(ExtensionName.DNSSEC_RETURN_STATUS, GetDNSConstants.GETDNS_EXTENSION_TRUE);
			HashMap<String, Object> info = context.generalSync("google.com", RRType.A, extensions);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			assertEquals(400, Integer.parseInt(GetDNSUtil.getObject(info, "/replies_tree[0]/dnssec_status").toString()));
		} finally {
			context.close();
		}

	}

	/**
	 * test with dnssec return only secure
	 */
	@Test
	public void testGetDNSWithDnssecOnlySecureExtension() {
		System.out.println("--------DNSSEC_RETURN_ONLY_SECURE--------------");
		final IGetDNSContext context = GetDNSFactory.create(1);
		try {
			HashMap<ExtensionName, Object> extensions = new HashMap<ExtensionName, Object>();
			extensions.put(ExtensionName.DNSSEC_RETURN_ONLY_SECURE, GetDNSConstants.GETDNS_EXTENSION_TRUE);
			HashMap<String, Object> info = context.generalSync("verisigninc.com", RRType.A, extensions);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			assertEquals(400, Integer.parseInt(GetDNSUtil.getObject(info, "/replies_tree[0]/dnssec_status").toString()));
		} finally {
			context.close();
		}

	}

	/**
	 * test with dnssec return validation chain
	 */
	@Test
	public void testGetDNSWithDnssecValidationChainExtension() {
		System.out.println("--------DNSSEC_RETURN_VALIDATIONCHAIN--------------");
		final IGetDNSContext context = GetDNSFactory.create(1);
		try {
			HashMap<ExtensionName, Object> extensions = new HashMap<ExtensionName, Object>();
			extensions.put(ExtensionName.DNSSEC_RETURN_VALIDATION_CHAIN, GetDNSConstants.GETDNS_EXTENSION_TRUE);
			HashMap<String, Object> info = context.generalSync("verisigninc.com", RRType.A, extensions);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			assertNotNull(GetDNSUtil.getObject(info, "/validation_chain"));
			System.out.println("Output: " + info);
		} finally {
			context.close();
		}

	}

	/**
	 * test with dnssec return status value by giving non dnssec enabled domain
	 */
	@Test
	public void testGetDNSWithDnssecStatusExtension2() {
		System.out.println("--------DNSSEC_RETURN_STATUS TEST--------------");
		final IGetDNSContext context = GetDNSFactory.create(1);
		try {
			HashMap<ExtensionName, Object> extensions = new HashMap<ExtensionName, Object>();
			extensions.put(ExtensionName.DNSSEC_RETURN_STATUS, GetDNSConstants.GETDNS_EXTENSION_TRUE);
			HashMap<String, Object> info = context.generalSync("google.com", RRType.A, extensions);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			assertEquals(403, Integer.parseInt(GetDNSUtil.getObject(info, "/replies_tree[0]/dnssec_status").toString()));
			System.out.println("Output: " + info);
		} finally {
			context.close();
		}

	}

	/**
	 * test with dnssec return only secure by giving non dnssec enabled domain
	 */
	@Test
	public void testGetDNSWithDnssecOnlySecureExtension2() {
		System.out.println("--------DNSSEC_RETURN_ONLY_SECURE--------------");
		HashMap<ContextOptionName, Object> options = new HashMap<ContextOptionName, Object>();
		options.put(ContextOptionName.STUB, true);
		final IGetDNSContext context = GetDNSFactory.create(1,options);
		try {
			HashMap<ExtensionName, Object> extensions = new HashMap<ExtensionName, Object>();
			extensions.put(ExtensionName.DNSSEC_RETURN_ONLY_SECURE, GetDNSConstants.GETDNS_EXTENSION_TRUE);
			HashMap<String, Object> info = context.generalSync("google.com", RRType.A, extensions);
			System.out.println(GetDNSUtil.printReadable(info));
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 903, Integer.parseInt(info.get("status").toString()));
		} finally {
			context.close();
		}

	}

	/**
	 * test with dnssec return validation chain by giving non dnssec enabled
	 * domain
	 */
	@Test
	public void testGetDNSWithDnssecValidationChainExtension2() {
		System.out.println("--------DNSSEC_RETURN_VALIDATIONCHAIN--------------");
		final IGetDNSContext context = GetDNSFactory.create(1);
		try {
			HashMap<ExtensionName, Object> extensions = new HashMap<ExtensionName, Object>();
			extensions.put(ExtensionName.DNSSEC_RETURN_VALIDATION_CHAIN, GetDNSConstants.GETDNS_EXTENSION_TRUE);
			HashMap<String, Object> info = context.generalSync("google.com", RRType.A, extensions);
			System.out.println("Output: " + info);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			assertEquals(0,GetDNSUtil.getAsListOfMap(info, "/validation_chain").size());

		} finally {
			context.close();
		}

	}

	/**
	 * test with return with V4 and V6
	 */
	@Test
	public void testGetDNSWithV4andV6Extension() {
		System.out.println("--------RETURN_WITH_V4_AND_V6--------------");
		final IGetDNSContext context = GetDNSFactory.create(1);
		try {
			HashMap<ExtensionName, Object> extensions = new HashMap<ExtensionName, Object>();
			extensions.put(ExtensionName.RETURN_BOTH_V4_AND_V6, GetDNSConstants.GETDNS_EXTENSION_TRUE);
			HashMap<String, Object> info = context.generalSync("google-public-dns-a.google.com.", RRType.A, extensions);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			assertEquals("IPv6", GetDNSUtil.getObject(info, "/just_address_answers[1]/address_type").toString());
		} finally {
			context.close();
		}

	}

	/**
	 * test with add_warning_for_bad_dns TO DO : need to find proper example
	 */
	@Test
	public void testGetDNSWithWarningExtension() {
		System.out.println("--------add_warning_for_bad_dns--------------");
		final IGetDNSContext context = GetDNSFactory.create(1);
		try {
			HashMap<ExtensionName, Object> extensions = new HashMap<ExtensionName, Object>();
			extensions.put(ExtensionName.ADD_WARNING_FOR_BAD_DNS, GetDNSConstants.GETDNS_EXTENSION_TRUE);
			HashMap<String, Object> info = context.generalSync("gagad%$#43", RRType.A, extensions);
			System.out.println("info:   " + info);
			assertNotNull(info);
		} finally {
			context.close();
		}

	}

	/**
	 * test with return_call_debugging
	 */
	@Test
	public void testGetDNSWithDebugginExtension() {
		System.out.println("--------RETURN_CALL_DEBUGGING--------------");
		final IGetDNSContext context = GetDNSFactory.create(1);
		try {
			HashMap<ExtensionName, Object> extensions = new HashMap<ExtensionName, Object>();
			extensions.put(ExtensionName.RETURN_CALL_DEBUGGING, GetDNSConstants.GETDNS_EXTENSION_TRUE);
			HashMap<String, Object> info = context.generalSync("verisigninc.com", RRType.A, extensions);
			assertNotNull(info);
			assertEquals("Time out error" + info.get("status"), 900, Integer.parseInt(info.get("status").toString()));
			// assertNotNull(GetDNSUtil.getinfovalues(info, "IPv6"));
		} finally {
			context.close();
		}

	}

	// @Test
	public void testGetDNSAsync() throws ExecutionException, TimeoutException {
		System.out.println("---------Starting testGetDNSAsync");
		final IGetDNSContext context = GetDNSFactory.create(1);

		try {
			//final CountDownLatch latch = new CountDownLatch(1);
			/*
			 * IGetDNSCallback callback = new IGetDNSCallback() {
			 * 
			 * public void handleResponse(HashMap<String, Object> response) {
			 * System.out
			 * .println("Completed domain with index: "+(1-latch.getCount()));
			 * latch.countDown(); System.out.println(response); }
			 * 
			 * public void handleException(GetDNSException exception) { // TODO
			 * Auto-generated method stub
			 * 
			 * } };
			 */
			long currentTime = System.currentTimeMillis();
			String[] domains = { "google.com", "facebook.com", "youtube.com", "yahoo.com", "baidu.com", "wikipedia.org",
					"twitter.com", "qq.com", "amazon.com", "live.com", "taobao.com", "linkedin.com", "google.co.in",
					"sina.com.cn", "hao123.com", "weibo.com", "blogspot.com", "tmall.com", "yahoo.co.jp", "sohu.com",
					"yandex.ru", "360.cn", "vk.com", "bing.com", "pinterest.com", "google.de", "wordpress.com", "ebay.com",
					"instagram.com", "soso.com", "google.co.uk", "google.co.jp", "paypal.com", "google.fr", "msn.com", "ask.com",
					"google.com.br", "163.com", "tumblr.com", "xvideos.com", "google.ru", "mail.ru", "microsoft.com", "imdb.com",
					"google.it", "stackoverflow.com", "google.es", "apple.com", "imgur.com", "reddit.com", "adcash.com",
					"craigslist.org", "blogger.com", "amazon.co.jp", "t.co", "aliexpress.com", "google.com.mx", "xhamster.com",
					"fc2.com", "google.ca", "gmw.cn", "wordpress.org", "cnn.com", "alibaba.com", "bbc.co.uk", "go.com",
					"huffingtonpost.com", "people.com.cn", "godaddy.com", "google.co.id", "kickass.to", "ifeng.com",
					"chinadaily.com.cn", "dropbox.com", "vube.com", "pornhub.com", "google.com.tr", "amazon.de",
					"themeforest.net", "xinhuanet.com", "adobe.com", "googleusercontent.com", "google.com.au", "netflix.com",
					"odnoklassniki.ru", "dailymotion.com", "google.pl", "thepiratebay.se", "booking.com", "ebay.de", "xnxx.com",
					"dailymail.co.uk", "flipkart.com", "about.com", "uol.com.br", "github.com", "espn.go.com", "google.com.hk",
					"bp.blogspot.com", "rakuten.co.jp", "vimeo.com", "akamaihd.net", "onclickads.net", "amazon.co.uk",
					"indiatimes.com", "flickr.com", "blogspot.in", "ebay.co.uk", "redtube.com", "tudou.com", "alipay.com",
					"fiverr.com", "clkmon.com", "salesforce.com", "outbrain.com", "nytimes.com", "buzzfeed.com", "google.com.ar",
					"globo.com", "pixnet.net", "cnet.com", "youporn.com", "google.com.sa", "aol.com", "sogou.com", "yelp.com",
					"gigacircle.com", "china.com", "google.com.eg", "google.com.tw", "pconline.com.cn", "mozilla.org",
					"livejasmin.com", "ameblo.jp", "w3schools.com", "secureserver.net", "google.nl", "so.com", "amazonaws.com",
					"slideshare.net", "theguardian.com", "wikia.com", "mailchimp.com", "directrev.com", "hootsuite.com",
					"google.com.pk", "bbc.com", "forbes.com", "mama.cn", "gmail.com", "yaolan.com", "adf.ly", "aili.com",
					"zol.com.cn", "livejournal.com", "weather.com", "mmbang.com", "soundcloud.com", "wikihow.com",
					"google.co.th", "livedoor.com", "google.co.za", "skype.com", "canadaalltax.com", "naver.com",
					"deviantart.com", "chase.com", "bankofamerica.com", "ettoday.net", "spiegel.de", "baomihua.com",
					"conduit.com", "stackexchange.com", "stumbleupon.com", "xcar.com.cn", "caijing.com.cn", "torrentz.eu",
					"9gag.com", "loading-delivery1.com", "foxnews.com", "businessinsider.com", "etsy.com", "hostgator.com",
					"walmart.com", "blogfa.com", "files.wordpress.com", "indeed.com", "mashable.com", "zillow.com", "badoo.com",
					"tripadvisor.com", "aweber.com", "sourceforge.net", "archive.org", "39.net", "reference.com", "china.com.cn",
					"moz.com", "amazon.in", "liveinternet.ru", "mediafire.com", "addthis.com", "wellsfargo.com",
					"shutterstock.com", "answers.com", "google.gr", "inclk.com", "statcounter.com", "rt.com", "gome.com.cn",
					"codecanyon.net", "onet.pl", "naver.jp", "pchome.net", "google.be", "jabong.com", "allegro.pl", "coccoc.com",
					"google.com.co", "google.com.my", "java.com", "4shared.com", "nicovideo.jp", "twitch.tv", "google.com.ua",
					"wikimedia.org", "wix.com", "doublepimp.com", "telegraph.co.uk", "google.com.ng", "avg.com", "bild.de",
					"wordreference.com", "dmm.co.jp", "google.com.vn", "ikea.com", "bitly.com", "avito.ru", "huanqiu.com",
					"yoka.com", "lady8844.com", "xgo.com.cn", "techcrunch.com", "bet365.com", "espncricinfo.com", "bitauto.com",
					"tube8.com", "popads.net", "quora.com", "jrj.com.cn", "warriorforum.com", "feedly.com", "google.co.kr",
					"force.com", "ask.fm", "leboncoin.fr", "bleacherreport.com", "v1.cn", "google.se", "php.net", "amazon.fr",
					"goo.ne.jp", "acesse.com", "google.at", "wsj.com", "enet.com.cn", "google.ro", "ci123.com", "snapdeal.com",
					"weebly.com", "google.com.ph", "zendesk.com", "pandora.com", "zeobit.com", "chinaz.com", "google.dz",
					"ups.com", "softonic.com", "xywy.com", "gogorithm.com", "washingtonpost.com", "google.cl", "google.com.pe",
					"wp.pl", "github.io", "photobucket.com", "usatoday.com", "mercadolivre.com.br", "goodreads.com",
					"hudong.com", "hurriyet.com.tr", "infusionsoft.com", "zedo.com", "google.com.sg", "speedtest.net", "gmx.net",
					"reuters.com", "pcbaby.com.cn", "disqus.com", "odesk.com", "douban.com", "daum.net", "goal.com", "usps.com",
					"google.ch", "meetup.com", "ndtv.com", "rev2pub.com", "kaskus.co.id", "media.tumblr.com", "gameforge.com",
					"rambler.ru", "extratorrent.cc", "google.pt", "rediff.com", "neobux.com", "comcast.net", "yesky.com",
					"domaintools.com", "google.cz", "jqw.com", "bluehost.com", "ehow.com", "ebay.in", "ign.com", "rutracker.org",
					"rbc.ru", "samsung.com", "microsoftonline.com", "gsmarena.com", "b5m.com", "fedex.com", "tmz.com",
					"time.com", "hdfcbank.com", "webmd.com", "mystart.com", "hp.com", "elance.com", "google.com.bd",
					"americanexpress.com", "marca.com", "stockstar.com", "bongacams.com", "histats.com", "scribd.com",
					"goodgamestudios.com", "varzesh3.com", "google.co.ve", "17ok.com", "detik.com", "icicibank.com",
					"bestbuy.com", "target.com", "milliyet.com.tr", "groupon.com", "trello.com", "tianya.cn", "repubblica.it",
					"cnzz.com", "evernote.com", "theladbible.com", "constantcontact.com", "probux.com", "goo.gl", "kompas.com",
					"web.de", "lifehacker.com", "lenta.ru", "nih.gov", "taringa.net", "olx.in", "uploaded.net", "fbcdn.net",
					"4dsply.com", "clickbank.com", "onlylady.com", "bloomberg.com", "webssearches.com", "myntra.com",
					"onlinesbi.com", "gfycat.com", "mlb.com", "turboloves.net", "getresponse.com", "abril.com.br", "kakaku.com",
					"thefreedictionary.com", "xuite.net", "free.fr", "cj.com", "youjizz.com", "chaturbate.com", "google.no",
					"google.az", "zippyshare.com", "clicksvenue.com", "elmundo.es", "subscene.com", "amazon.cn", "elpais.com",
					"capitalone.com", "sahibinden.com", "cpmterra.com", "list-manage.com", "google.ie", "google.co.hu",
					"iqiyi.com", "udn.com", "motherless.com", "ebay.it", "icmwebserv.com", "google.ae", "okcupid.com",
					"steamcommunity.com", "zeroredirect1.com", "xing.com", "rednet.cn", "abcnews.go.com", "ameba.jp", "ria.ru",
					"homedepot.com", "accuweather.com", "lemonde.fr", "quikr.com", "habrahabr.ru", "gizmodo.com", "youth.cn",
					"chinabyte.com", "voc.com.cn", "hulu.com", "libero.it", "privatehomeclips.com", "pof.com", "semrush.com",
					"likes.com", "taboola.com", "ebay.com.au", "beeg.com", "engadget.com", "yandex.ua", "dell.com", "trovi.com",
					"youm7.com", "gazeta.pl", "4399.com", "drudgereport.com", "amazon.it", "blackhatworld.com", "issuu.com",
					"surveymonkey.com", "trulia.com", "lefigaro.fr", "sabq.org", "steampowered.com", "lenovo.com", "seznam.cz",
					"expedia.com", "intuit.com", "ucoz.ru", "orange.fr", "buyma.com", "hotels.com", "xe.com", "kickstarter.com",
					"blogspot.co.uk", "t-online.de", "naukri.com", "v9.com", "doubleclick.com", "google.co.il", "cloudfront.net",
					"liputan6.com", "webmoney.ru", "hubspot.com", "9gag.tv", "shareasale.com", "retailmenot.com", "chexun.com",
					"vice.com", "google.dk", "intoday.in", "52pk.net", "asana.com", "uimserv.net", "ero-advertising.com",
					"51fanli.com", "xda-developers.com", "ganji.com", "istockphoto.com", "w3.org", "it168.com", "basecamp.com",
					"att.com", "twimg.com", "youboy.com", "blogspot.mx", "rottentomatoes.com", "oracle.com", "zoho.com" };
			ArrayList<GetDNSFutureResult> results = new ArrayList<>();
			int i = 0;
			for (String domain : domains) {

				results.add(context.generalAsync(domain, RRType.A, null));
				if (++i == 20)
					break;
			}
			for (GetDNSFutureResult result : results) {

				try {
					System.out.println(result.get(5000, null));
					/*
					 * for (i=0;i<5;i++) { System.out.println("Sleeping.. " + i);
					 * Thread.sleep(1000); if(result.isDone()){ break; } } if(i==5)
					 * System.out.println("No result");
					 */
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("Total time: " + (System.currentTimeMillis() - currentTime));
		} finally {
			context.close();
		}

	}

	/*
	 * @Test public void testGetDNSBulk() {
	 * System.out.println("---------Starting testGetDNSBulk"); GetDNS getDNS = new
	 * GetDNS(); Object context = getDNS.contextCreate(1); // invoke the native
	 * method try{ String[] domains = {"google.com", "facebook.com",
	 * "youtube.com", "yahoo.com", "baidu.com", "wikipedia.org", "twitter.com",
	 * "qq.com", "amazon.com"};//, "live.com", "taobao.com", "linkedin.com",
	 * "google.co.in", "sina.com.cn", "hao123.com", "weibo.com", "blogspot.com",
	 * "tmall.com", "yahoo.co.jp", "sohu.com", "yandex.ru", "360.cn", "vk.com",
	 * "bing.com", "pinterest.com", "google.de", "wordpress.com", "ebay.com",
	 * "instagram.com", "soso.com", "google.co.uk", "google.co.jp", "paypal.com",
	 * "google.fr", "msn.com", "ask.com", "google.com.br", "163.com",
	 * "tumblr.com", "xvideos.com", "google.ru", "mail.ru", "microsoft.com",
	 * "imdb.com", "google.it", "stackoverflow.com", "google.es",
	 * "apple.com","imgur.com"
	 * ,"reddit.com","adcash.com","craigslist.org","blogger.com"
	 * ,"amazon.co.jp","t.co"
	 * ,"aliexpress.com","google.com.mx","xhamster.com","fc2.com"
	 * ,"google.ca","gmw.cn"
	 * ,"wordpress.org","cnn.com","alibaba.com","bbc.co.uk","go.com"
	 * ,"huffingtonpost.com"
	 * ,"people.com.cn","godaddy.com","google.co.id","kickass.to"
	 * ,"ifeng.com","chinadaily.com.cn"
	 * ,"dropbox.com","vube.com","pornhub.com","google.com.tr"
	 * ,"amazon.de","themeforest.net"
	 * ,"xinhuanet.com","adobe.com","googleusercontent.com"
	 * ,"google.com.au","netflix.com"
	 * ,"odnoklassniki.ru","dailymotion.com","google.pl"
	 * ,"thepiratebay.se","booking.com"
	 * ,"ebay.de","xnxx.com","dailymail.co.uk","flipkart.com"
	 * ,"about.com","uol.com.br"
	 * ,"github.com","espn.go.com","google.com.hk","bp.blogspot.com"
	 * ,"rakuten.co.jp"
	 * ,"vimeo.com","akamaihd.net","onclickads.net","amazon.co.uk",
	 * "indiatimes.com"
	 * ,"flickr.com","blogspot.in","ebay.co.uk","redtube.com","tudou.com"
	 * ,"alipay.com"
	 * ,"fiverr.com","clkmon.com","salesforce.com","outbrain.com","nytimes.com"
	 * ,"buzzfeed.com"
	 * ,"google.com.ar","globo.com","pixnet.net","cnet.com","youporn.com"
	 * ,"google.com.sa"
	 * ,"aol.com","sogou.com","yelp.com","gigacircle.com","china.com"
	 * ,"google.com.eg"
	 * ,"google.com.tw","pconline.com.cn","mozilla.org","livejasmin.com"
	 * ,"ameblo.jp"
	 * ,"w3schools.com","secureserver.net","google.nl","so.com","amazonaws.com"
	 * ,"slideshare.net"
	 * ,"theguardian.com","wikia.com","mailchimp.com","directrev.com"
	 * ,"hootsuite.com"
	 * ,"google.com.pk","bbc.com","forbes.com","mama.cn","gmail.com"
	 * ,"yaolan.com","adf.ly"
	 * ,"aili.com","zol.com.cn","livejournal.com","weather.com"
	 * ,"mmbang.com","soundcloud.com"
	 * ,"wikihow.com","google.co.th","livedoor.com","google.co.za"
	 * ,"skype.com","canadaalltax.com"
	 * ,"naver.com","deviantart.com","chase.com","bankofamerica.com"
	 * ,"ettoday.net",
	 * "spiegel.de","baomihua.com","conduit.com","stackexchange.com"
	 * ,"stumbleupon.com","xcar.com.cn","caijing.com.cn","torrentz.eu","9gag.com",
	 * "loading-delivery1.com"
	 * ,"foxnews.com","businessinsider.com","etsy.com","hostgator.com"
	 * ,"walmart.com"
	 * ,"blogfa.com","files.wordpress.com","indeed.com","mashable.com"
	 * ,"zillow.com"
	 * ,"badoo.com","tripadvisor.com","aweber.com","sourceforge.net","archive.org"
	 * ,
	 * "39.net","reference.com","china.com.cn","moz.com","amazon.in","liveinternet.ru"
	 * ,
	 * "mediafire.com","addthis.com","wellsfargo.com","shutterstock.com","answers.com"
	 * ,"google.gr","inclk.com","statcounter.com","rt.com","gome.com.cn",
	 * "codecanyon.net"
	 * ,"onet.pl","naver.jp","pchome.net","google.be","jabong.com",
	 * "allegro.pl","coccoc.com"
	 * ,"google.com.co","google.com.my","java.com","4shared.com"
	 * ,"nicovideo.jp","twitch.tv"
	 * ,"google.com.ua","wikimedia.org","wix.com","doublepimp.com"
	 * ,"telegraph.co.uk"
	 * ,"google.com.ng","avg.com","bild.de","wordreference.com","dmm.co.jp"
	 * ,"google.com.vn"
	 * ,"ikea.com","bitly.com","avito.ru","huanqiu.com","yoka.com",
	 * "lady8844.com","xgo.com.cn"
	 * ,"techcrunch.com","bet365.com","espncricinfo.com"
	 * ,"bitauto.com","tube8.com",
	 * "popads.net","quora.com","jrj.com.cn","warriorforum.com"
	 * ,"feedly.com","google.co.kr"
	 * ,"force.com","ask.fm","leboncoin.fr","bleacherreport.com"
	 * ,"v1.cn","google.se"
	 * ,"php.net","amazon.fr","goo.ne.jp","acesse.com","google.at"
	 * ,"wsj.com","enet.com.cn"
	 * ,"google.ro","ci123.com","snapdeal.com","weebly.com"
	 * ,"google.com.ph","zendesk.com"
	 * ,"pandora.com","zeobit.com","chinaz.com","google.dz"
	 * ,"ups.com","softonic.com"
	 * ,"xywy.com","gogorithm.com","washingtonpost.com","google.cl"
	 * ,"google.com.pe"
	 * ,"wp.pl","github.io","photobucket.com","usatoday.com","mercadolivre.com.br"
	 * ,
	 * "goodreads.com","hudong.com","hurriyet.com.tr","infusionsoft.com","zedo.com"
	 * ,"google.com.sg","speedtest.net","gmx.net","reuters.com","pcbaby.com.cn",
	 * "disqus.com"
	 * ,"odesk.com","douban.com","daum.net","goal.com","usps.com","google.ch"
	 * ,"meetup.com"
	 * ,"ndtv.com","rev2pub.com","kaskus.co.id","media.tumblr.com","gameforge.com"
	 * ,"rambler.ru","extratorrent.cc","google.pt","rediff.com","neobux.com",
	 * "comcast.net"
	 * ,"yesky.com","domaintools.com","google.cz","jqw.com","bluehost.com"
	 * ,"ehow.com","ebay.in","ign.com","rutracker.org","rbc.ru","samsung.com",
	 * "microsoftonline.com"
	 * ,"gsmarena.com","b5m.com","fedex.com","tmz.com","time.com"
	 * ,"hdfcbank.com","webmd.com"
	 * ,"mystart.com","hp.com","elance.com","google.com.bd"
	 * ,"americanexpress.com","marca.com"
	 * ,"stockstar.com","bongacams.com","histats.com"
	 * ,"scribd.com","goodgamestudios.com"
	 * ,"varzesh3.com","google.co.ve","17ok.com"
	 * ,"detik.com","icicibank.com","bestbuy.com"
	 * ,"target.com","milliyet.com.tr","groupon.com"
	 * ,"trello.com","tianya.cn","repubblica.it"
	 * ,"cnzz.com","evernote.com","theladbible.com"
	 * ,"constantcontact.com","probux.com"
	 * ,"goo.gl","kompas.com","web.de","lifehacker.com"
	 * ,"lenta.ru","nih.gov","taringa.net"
	 * ,"olx.in","uploaded.net","fbcdn.net","4dsply.com"
	 * ,"clickbank.com","onlylady.com"
	 * ,"bloomberg.com","webssearches.com","myntra.com"
	 * ,"onlinesbi.com","gfycat.com"
	 * ,"mlb.com","turboloves.net","getresponse.com","abril.com.br"
	 * ,"kakaku.com","thefreedictionary.com"
	 * ,"xuite.net","free.fr","cj.com","youjizz.com"
	 * ,"chaturbate.com","google.no","google.az"
	 * ,"zippyshare.com","clicksvenue.com"
	 * ,"elmundo.es","subscene.com","amazon.cn",
	 * "elpais.com","capitalone.com","sahibinden.com"
	 * ,"cpmterra.com","list-manage.com"
	 * ,"google.ie","google.co.hu","iqiyi.com","udn.com"
	 * ,"motherless.com","ebay.it"
	 * ,"icmwebserv.com","google.ae","okcupid.com","steamcommunity.com"
	 * ,"zeroredirect1.com"
	 * ,"xing.com","rednet.cn","abcnews.go.com","ameba.jp","ria.ru"
	 * ,"homedepot.com"
	 * ,"accuweather.com","lemonde.fr","quikr.com","habrahabr.ru","gizmodo.com"
	 * ,"youth.cn"
	 * ,"chinabyte.com","voc.com.cn","hulu.com","libero.it","privatehomeclips.com"
	 * ,
	 * "pof.com","semrush.com","likes.com","taboola.com","ebay.com.au","beeg.com",
	 * "engadget.com"
	 * ,"yandex.ua","dell.com","trovi.com","youm7.com","gazeta.pl","4399.com"
	 * ,"drudgereport.com"
	 * ,"amazon.it","blackhatworld.com","issuu.com","surveymonkey.com"
	 * ,"trulia.com"
	 * ,"lefigaro.fr","sabq.org","steampowered.com","lenovo.com","seznam.cz"
	 * ,"expedia.com"
	 * ,"intuit.com","ucoz.ru","orange.fr","buyma.com","hotels.com","xe.com"
	 * ,"kickstarter.com"
	 * ,"blogspot.co.uk","t-online.de","naukri.com","v9.com","doubleclick.com"
	 * ,"google.co.il"
	 * ,"cloudfront.net","liputan6.com","webmoney.ru","hubspot.com",
	 * "9gag.tv","shareasale.com"
	 * ,"retailmenot.com","chexun.com","vice.com","google.dk"
	 * ,"intoday.in","52pk.net"
	 * ,"asana.com","uimserv.net","ero-advertising.com","51fanli.com"
	 * ,"xda-developers.com"
	 * ,"ganji.com","istockphoto.com","w3.org","it168.com","basecamp.com"
	 * ,"att.com"
	 * ,"twimg.com","youboy.com","blogspot.mx","rottentomatoes.com","oracle.com"
	 * ,"zoho.com"};
	 * 
	 * long start = System.currentTimeMillis(); for(String domain:domains){
	 * 
	 * HashMap<String, Object> info = getDNS.generalSync(context, domain, 1,
	 * null); System.out.println("Output: "+ info); }
	 * System.out.println("Avg Time taken per domain: "
	 * +((System.currentTimeMillis()-start)/(1000.0*domains.length))+" seconds");
	 * 
	 * } finally{ getDNS.contextDestroy(context); } }
	 */
}
