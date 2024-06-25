package com.tangerine.core.model
import com.tangerine.core.source.R

enum class Language(val code: String, val title: Int, val appName: String) {
    TAIWAN("zh-tw", R.string.chinese_taiwan, "台灣旅遊"),
    CHINESE("zh-cn", R.string.chinese_prc, "台湾之旅"),
    ENGLISH("en", R.string.english,"Taiwan tour"),
    JAPAN("ja", R.string.japanese, "台湾ツアー"),
    KOREAN("ko", R.string.korean, "대만 여행"),
    SPANISH("es", R.string.spanish, "tour por Taiwán"),
    INDONESIAN("id",R.string.indonesian, "Tur Taiwan"),
    THAI("th",R.string.thai, "ทัวร์ไต้หวัน"),
    VIETNAMESE("vi", R.string.vietnamese,"Tour Đài Loan");

    companion object {
        fun getLanguageFromOrdinal(ordinal: Int) = Language.values().firstOrNull { it.ordinal == ordinal } ?: ENGLISH
        fun getLanguageFromCode(code: String) = Language.values().firstOrNull { it.code == code } ?: ENGLISH
    }
}

enum class AnimType { SLIDE_LEFT, SLIDE_RIGHT, SLIDE_BOTTOM, SLIDE_TOP, FADE }

enum class UiState { LOADING, SUCCESS, ERROR }