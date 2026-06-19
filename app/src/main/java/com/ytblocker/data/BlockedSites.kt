package com.ytblocker.data

/**
 * Centralized block list of apps and websites to block.
 * Organized by category for maintainability.
 */
object BlockedSites {

    // ── Blocked App Package Names ──────────────────────────────────────

    val BLOCKED_PACKAGES: Map<String, String> = mapOf(
        // YouTube
        "com.google.android.youtube" to "YOUTUBE",
        "com.google.android.youtube.tv" to "YOUTUBE",
        "com.google.android.youtube.creator" to "YOUTUBE",
        "com.google.android.apps.youtube.music" to "YOUTUBE MUSIC",
        "com.google.android.apps.youtube.kids" to "YOUTUBE KIDS",

        // Gambling & Betting
        "com.bet365.mobile" to "GAMBLING",
        "com.fanduel.sportsbook" to "GAMBLING",
        "com.draftkings.sportsbook" to "GAMBLING",
        "com.betmgm.sportsbook" to "GAMBLING",
        "com.williamhill.sports" to "GAMBLING",
        "com.paddypower.sportsbook" to "GAMBLING",
        "com.betfair.sportsbook" to "GAMBLING",
        "com.pokerstars.mobile" to "GAMBLING",
        "com.888.poker" to "GAMBLING",
        "com.bovada.mobile" to "GAMBLING",
        "com.caesars.sportsbook" to "GAMBLING",
        "com.pointsbet.sportsbook" to "GAMBLING",
        "com.bwin.sports" to "GAMBLING",
        "com.unibet.sports" to "GAMBLING",
        "com.betway.sport" to "GAMBLING",
        "com.stake.app" to "GAMBLING",
        "com.1xbet.mobile" to "GAMBLING",

        // Porn & Adult Content
        "com.pornhub.android" to "ADULT CONTENT",
        "com.xvideos.app" to "ADULT CONTENT",
        "com.xnxx.app" to "ADULT CONTENT",
        "com.onlyfans.app" to "ADULT CONTENT",
        "com.stripchat.app" to "ADULT CONTENT",

        // Dating Apps
        "com.tinder" to "DATING",
        "com.bumble.app" to "DATING",
        "com.badoo.mobile" to "DATING",
        "com.match.android" to "DATING",
        "com.hinge.app" to "DATING",
        "com.okcupid.okcupid" to "DATING",
        "com.grindr.android" to "DATING",
        "com.plenty.of.fish" to "DATING",
        "com.zoosk.zoosk" to "DATING",
        "com.coffee.meets.bagel" to "DATING",

        // Social Media (Addictive / Time-Wasting)
        "com.zhiliaoapp.musically" to "TIKTOK",
        "com.ss.android.ugc.trill" to "TIKTOK",
        "com.instagram.android" to "INSTAGRAM",
        "com.snapchat.android" to "SNAPCHAT",
        "com.twitter.android" to "X/TWITTER",
        "com.reddit.frontpage" to "REDDIT",
        "com.tumblr" to "TUMBLR",

        // Ad-heavy / Clickbait Apps
        "com.taboola.android" to "ADS",
        "com.outbrain.app" to "ADS",

        // Piracy / Torrenting
        "com.utorrent.client" to "PIRACY",
        "org.proninyaroslav.libretorrent" to "PIRACY",
        "com.bittorrent.client" to "PIRACY",
        "com.vuze.android.client" to "PIRACY",
        "org.transdroid.full" to "PIRACY",

        // VPNs, Proxies, and Tor (Used to bypass blocking)
        "com.nordvpn.android" to "VPN / PROXY",
        "com.expressvpn.vpn" to "VPN / PROXY",
        "org.hola.vpn" to "VPN / PROXY",
        "com.surfshark.vpnclient.android" to "VPN / PROXY",
        "ch.protonvpn.android" to "VPN / PROXY",
        "com.windscribe.vpn" to "VPN / PROXY",
        "hotspotshield.android.vpn" to "VPN / PROXY",
        "com.cyberghost.vpn" to "VPN / PROXY",
        "com.privateinternetaccess.android" to "VPN / PROXY",
        "com.tunnelbear.android" to "VPN / PROXY",
        "com.freevpn.android" to "VPN / PROXY",
        "com.supervpn.vpn" to "VPN / PROXY",
        "com.secureconnection.vpn" to "VPN / PROXY",
        "org.torproject.torbrowser" to "TOR BROWSER",
        "org.torproject.torbrowser_alpha" to "TOR BROWSER",
        "org.torproject.android" to "ORBOT",
    )

    // ── Blocked Website URL Domains ────────────────────────────────────
    // These are checked against the browser URL bar content.
    // Each entry is a pair of (domain keyword, category label).

    data class BlockedDomain(val domain: String, val category: String)

    val BLOCKED_DOMAINS: List<BlockedDomain> = listOf(
        // ── YouTube ──
        BlockedDomain("youtube.com", "YOUTUBE"),
        BlockedDomain("youtu.be", "YOUTUBE"),
        BlockedDomain("m.youtube.com", "YOUTUBE"),
        BlockedDomain("music.youtube.com", "YOUTUBE MUSIC"),

        // ── Gambling & Betting ──
        BlockedDomain("bet365.com", "GAMBLING"),
        BlockedDomain("fanduel.com", "GAMBLING"),
        BlockedDomain("draftkings.com", "GAMBLING"),
        BlockedDomain("betmgm.com", "GAMBLING"),
        BlockedDomain("williamhill.com", "GAMBLING"),
        BlockedDomain("paddypower.com", "GAMBLING"),
        BlockedDomain("betfair.com", "GAMBLING"),
        BlockedDomain("pokerstars.com", "GAMBLING"),
        BlockedDomain("888.com", "GAMBLING"),
        BlockedDomain("888poker.com", "GAMBLING"),
        BlockedDomain("bovada.lv", "GAMBLING"),
        BlockedDomain("caesars.com", "GAMBLING"),
        BlockedDomain("pointsbet.com", "GAMBLING"),
        BlockedDomain("bwin.com", "GAMBLING"),
        BlockedDomain("unibet.com", "GAMBLING"),
        BlockedDomain("betway.com", "GAMBLING"),
        BlockedDomain("stake.com", "GAMBLING"),
        BlockedDomain("1xbet.com", "GAMBLING"),
        BlockedDomain("betonline.ag", "GAMBLING"),
        BlockedDomain("ladbrokes.com", "GAMBLING"),
        BlockedDomain("coral.co.uk", "GAMBLING"),
        BlockedDomain("betfred.com", "GAMBLING"),
        BlockedDomain("skybet.com", "GAMBLING"),
        BlockedDomain("sportingbet.com", "GAMBLING"),
        BlockedDomain("marathonbet.com", "GAMBLING"),
        BlockedDomain("betclic.com", "GAMBLING"),
        BlockedDomain("sportsbet.com.au", "GAMBLING"),
        BlockedDomain("neds.com.au", "GAMBLING"),
        BlockedDomain("tab.com.au", "GAMBLING"),
        BlockedDomain("gambling.com", "GAMBLING"),
        BlockedDomain("casinoroom.com", "GAMBLING"),
        BlockedDomain("casumo.com", "GAMBLING"),
        BlockedDomain("leovegas.com", "GAMBLING"),
        BlockedDomain("mrgreen.com", "GAMBLING"),
        BlockedDomain("jackpotcity.com", "GAMBLING"),
        BlockedDomain("spinpalace.com", "GAMBLING"),
        BlockedDomain("royalpanda.com", "GAMBLING"),
        BlockedDomain("betsson.com", "GAMBLING"),

        // ── Pornography & Adult Content ──
        BlockedDomain("pornhub.com", "ADULT CONTENT"),
        BlockedDomain("xvideos.com", "ADULT CONTENT"),
        BlockedDomain("xnxx.com", "ADULT CONTENT"),
        BlockedDomain("xhamster.com", "ADULT CONTENT"),
        BlockedDomain("redtube.com", "ADULT CONTENT"),
        BlockedDomain("youporn.com", "ADULT CONTENT"),
        BlockedDomain("tube8.com", "ADULT CONTENT"),
        BlockedDomain("spankbang.com", "ADULT CONTENT"),
        BlockedDomain("eporner.com", "ADULT CONTENT"),
        BlockedDomain("hentaihaven.xxx", "ADULT CONTENT"),
        BlockedDomain("rule34.xxx", "ADULT CONTENT"),
        BlockedDomain("nhentai.net", "ADULT CONTENT"),
        BlockedDomain("chaturbate.com", "ADULT CONTENT"),
        BlockedDomain("stripchat.com", "ADULT CONTENT"),
        BlockedDomain("cam4.com", "ADULT CONTENT"),
        BlockedDomain("myfreecams.com", "ADULT CONTENT"),
        BlockedDomain("livejasmin.com", "ADULT CONTENT"),
        BlockedDomain("bongacams.com", "ADULT CONTENT"),
        BlockedDomain("onlyfans.com", "ADULT CONTENT"),
        BlockedDomain("fansly.com", "ADULT CONTENT"),
        BlockedDomain("manyvids.com", "ADULT CONTENT"),
        BlockedDomain("brazzers.com", "ADULT CONTENT"),
        BlockedDomain("realitykings.com", "ADULT CONTENT"),
        BlockedDomain("bangbros.com", "ADULT CONTENT"),
        BlockedDomain("naughtyamerica.com", "ADULT CONTENT"),
        BlockedDomain("mofos.com", "ADULT CONTENT"),
        BlockedDomain("sexcom.com", "ADULT CONTENT"),
        BlockedDomain("sex.com", "ADULT CONTENT"),
        BlockedDomain("4chan.org", "ADULT CONTENT"),
        BlockedDomain("motherless.com", "ADULT CONTENT"),
        BlockedDomain("tnaflix.com", "ADULT CONTENT"),
        BlockedDomain("drtuber.com", "ADULT CONTENT"),
        BlockedDomain("thumbzilla.com", "ADULT CONTENT"),
        BlockedDomain("beeg.com", "ADULT CONTENT"),
        BlockedDomain("txxx.com", "ADULT CONTENT"),
        BlockedDomain("hqporner.com", "ADULT CONTENT"),
        BlockedDomain("daftsex.com", "ADULT CONTENT"),
        BlockedDomain("alohatube.com", "ADULT CONTENT"),
        BlockedDomain("fuq.com", "ADULT CONTENT"),
        BlockedDomain("heavy-r.com", "ADULT CONTENT"),
        BlockedDomain("erothots.com", "ADULT CONTENT"),
        BlockedDomain("fapello.com", "ADULT CONTENT"),
        BlockedDomain("coomer.su", "ADULT CONTENT"),
        BlockedDomain("simpcity.su", "ADULT CONTENT"),

        // ── Dating Sites ──
        BlockedDomain("tinder.com", "DATING"),
        BlockedDomain("bumble.com", "DATING"),
        BlockedDomain("badoo.com", "DATING"),
        BlockedDomain("match.com", "DATING"),
        BlockedDomain("hinge.co", "DATING"),
        BlockedDomain("okcupid.com", "DATING"),
        BlockedDomain("grindr.com", "DATING"),
        BlockedDomain("pof.com", "DATING"),
        BlockedDomain("zoosk.com", "DATING"),
        BlockedDomain("coffeemeetsbagel.com", "DATING"),
        BlockedDomain("eharmony.com", "DATING"),
        BlockedDomain("elitesingles.com", "DATING"),
        BlockedDomain("happn.com", "DATING"),

        // ── Addictive Social Media ──
        BlockedDomain("tiktok.com", "TIKTOK"),
        BlockedDomain("instagram.com", "INSTAGRAM"),
        BlockedDomain("snapchat.com", "SNAPCHAT"),
        BlockedDomain("twitter.com", "X/TWITTER"),
        BlockedDomain("x.com", "X/TWITTER"),
        BlockedDomain("reddit.com", "REDDIT"),
        BlockedDomain("tumblr.com", "TUMBLR"),

        // ── Ad Networks / Clickbait ──
        BlockedDomain("taboola.com", "ADS"),
        BlockedDomain("outbrain.com", "ADS"),
        BlockedDomain("revcontent.com", "ADS"),
        BlockedDomain("mgid.com", "ADS"),
        BlockedDomain("content.ad", "ADS"),
        BlockedDomain("adnow.com", "ADS"),

        // ── Piracy / Torrenting ──
        BlockedDomain("thepiratebay.org", "PIRACY"),
        BlockedDomain("1337x.to", "PIRACY"),
        BlockedDomain("rarbg.to", "PIRACY"),
        BlockedDomain("nyaa.si", "PIRACY"),
        BlockedDomain("yts.mx", "PIRACY"),
        BlockedDomain("fitgirl-repacks.site", "PIRACY"),
        BlockedDomain("fmovies.to", "PIRACY"),
        BlockedDomain("123movies.to", "PIRACY"),
        BlockedDomain("putlocker.to", "PIRACY"),
        BlockedDomain("soap2day.to", "PIRACY"),
        BlockedDomain("bflix.to", "PIRACY"),
        BlockedDomain("lookmovie.io", "PIRACY"),
        BlockedDomain("solarmovie.pe", "PIRACY"),
        BlockedDomain("gomovies.to", "PIRACY"),
        BlockedDomain("flixtor.to", "PIRACY"),
        BlockedDomain("kimcartoon.to", "PIRACY"),
        BlockedDomain("9anime.to", "PIRACY"),
        BlockedDomain("gogoanime.run", "PIRACY"),
        BlockedDomain("animepahe.ru", "PIRACY"),
        BlockedDomain("zoro.to", "PIRACY"),

        // ── Scam / Phishing / Malware common domains ──
        BlockedDomain("wish.com", "SCAM"),
        BlockedDomain("temu.com", "SCAM"),

        // ── Proxies, VPNs, and Tor Web Tools ──
        BlockedDomain("kproxy.com", "PROXY / VPN"),
        BlockedDomain("proxysite.com", "PROXY / VPN"),
        BlockedDomain("croxyproxy.com", "PROXY / VPN"),
        BlockedDomain("webproxy.to", "PROXY / VPN"),
        BlockedDomain("filterbypass.me", "PROXY / VPN"),
        BlockedDomain("hidemy.name", "PROXY / VPN"),
        BlockedDomain("blockaway.net", "PROXY / VPN"),
        BlockedDomain("proxyium.com", "PROXY / VPN"),
        BlockedDomain("vpnbook.com", "PROXY / VPN"),
        BlockedDomain("whoer.net", "PROXY / VPN"),
        BlockedDomain("tunnelbear.com", "PROXY / VPN"),
        BlockedDomain("nordvpn.com", "PROXY / VPN"),
        BlockedDomain("expressvpn.com", "PROXY / VPN"),
        BlockedDomain("surfshark.com", "PROXY / VPN"),
        BlockedDomain("protonvpn.com", "PROXY / VPN"),
        BlockedDomain("cyberghostvpn.com", "PROXY / VPN"),
        BlockedDomain("windscribe.com", "PROXY / VPN"),
        BlockedDomain("hotspotshield.com", "PROXY / VPN"),
        BlockedDomain("hidester.com", "PROXY / VPN"),
        BlockedDomain("hidester.one", "PROXY / VPN"),
        BlockedDomain("megaproxy.com", "PROXY / VPN"),
        BlockedDomain("zend2.com", "PROXY / VPN"),
        BlockedDomain("4everproxy.com", "PROXY / VPN"),
        BlockedDomain("proxyfree.com", "PROXY / VPN"),
        BlockedDomain("hide.me", "PROXY / VPN"),
        BlockedDomain("proxy-cleaner.com", "PROXY / VPN"),
        BlockedDomain("proxy.toolur.com", "PROXY / VPN"),
        BlockedDomain("fastusa.site", "PROXY / VPN"),
        BlockedDomain("proxyscrape.com", "PROXY / VPN"),
        BlockedDomain("hidemyna.me", "PROXY / VPN"),
        BlockedDomain("online-proxy.com", "PROXY / VPN"),
        BlockedDomain("123proxy.net", "PROXY / VPN"),
        BlockedDomain("hidemyass.com", "PROXY / VPN"),
        BlockedDomain("torproject.org", "PROXY / VPN"),
    )

    /**
     * Check if a URL string matches any blocked domain.
     * Returns the category name or null if not blocked.
     */
    fun getBlockedCategory(url: String): String? {
        val lowerUrl = url.lowercase()
        for (entry in BLOCKED_DOMAINS) {
            if (lowerUrl.contains(entry.domain)) {
                return entry.category
            }
        }
        return null
    }

    /**
     * Check if a package name is blocked.
     * Returns the category name or null if not blocked.
     */
    fun getBlockedPackageCategory(packageName: String): String? {
        return BLOCKED_PACKAGES[packageName]
    }
}
