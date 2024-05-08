package kz.rdd.core.utils.ext

import com.google.i18n.phonenumbers.PhoneNumberUtil
import kz.rdd.core.utils.consts.CountryCode

private val countryCodesMap by lazy {
    CountryCode.entries.associateBy {
        it.code
    }
}

fun String.getPhoneNumberWithCountryCode(): Pair<CountryCode, String>? {
    return try {
        val phoneUtil = PhoneNumberUtil.getInstance()
        val parsedPhone = phoneUtil.parse(this, "KZ")
        val countryCode = countryCodesMap["+${parsedPhone.countryCode}"] ?: return null
        countryCode to parsedPhone.nationalNumber.toString()
    } catch (_: Exception) {
        null
    }
}
