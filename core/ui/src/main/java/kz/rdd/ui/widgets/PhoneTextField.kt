package kz.rdd.core.ui.widgets

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import kz.rdd.core.ui.R
import kz.rdd.core.ui.ext.clickableWithIndication
import kz.rdd.core.ui.ext.scaledClickable
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.utils.MaskVisualTransformation
import kz.rdd.core.utils.consts.CountryCode

@Composable
fun PhoneTextField(
    value: String,
    onUpdate: (String) -> Unit,
    modifier: Modifier = Modifier,
    afterModifier: Modifier = Modifier,
    fieldModifier: Modifier = Modifier,
    countryCode: CountryCode = CountryCode.KZ,
    hintChar: Char = '0',
    hasError: Boolean = false,
    errorMessage: String? = null,
    isEnabled: Boolean = true,
    readOnly: Boolean = false,
    onClick: (() -> Unit)? = null,
    imeAction: ImeAction = ImeAction.Default,
    onUpdateCountryCode: ((CountryCode) -> Unit)? = null,
    textStyle: TextStyle = LocalAppTheme.typography.l15,
    placeholderTextStyle: TextStyle = LocalAppTheme.typography.l15,
    trailingIcon: @Composable (() -> Unit)? = null,
) {

    var isDropDownShown by remember {
        mutableStateOf(false)
    }

    val hintColor = LocalAppTheme.colors.accentText

    val visualTransformation = remember(countryCode) {
        MaskVisualTransformation(
            prefix = "",
            mask = countryCode.mask,
            maskChar = '#',
            hintChar = hintChar,
            hintColor = hintColor,
        )
    }

    BoxWithConstraints(
        modifier = modifier.fillMaxWidth().then(afterModifier)
    ) {
        CommonTextField(
            value = value,
            onUpdate = {
                if (it.length <= countryCode.numbersSize) onUpdate(it)
            },
            placeholderText = "",
            hasError = hasError,
            errorMessage = errorMessage,
            isEnabled = isEnabled,
            readOnly = readOnly,
            onClick = onClick,
            textStyle = textStyle,
            placeholderTextStyle = placeholderTextStyle,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = imeAction,
            ),
            visualTransformation = visualTransformation,
            leadingContent = {
                Row(
                    modifier = Modifier
                        .scaledClickable(
                            enabled = !readOnly,
                        ) {
                            isDropDownShown = true
                        }
                        .padding(start = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = countryCode.code,
                        style = LocalAppTheme.typography.l16,
                        color = LocalAppTheme.colors.primaryText,
                        modifier = Modifier.animateContentSize()
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = R.drawable.ic_chevron_bottom
                        ),
                        contentDescription = null,
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Spacer(
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                            .height(24.dp)
                            .width(1.dp)
                            .background(LocalAppTheme.colors.stroke),
                    )
                }
            },
            trailingIcon = trailingIcon,
            modifier = fieldModifier,
        )


        val countryCodes = remember {
            CountryCode.entries
        }
        val shape = remember {
            RoundedCornerShape(10.dp)
        }
        MaterialTheme(
            shapes = MaterialTheme.shapes.copy(medium = shape),
        ) {
            CommonDropdownMenu(
                expanded = isDropDownShown,
                offset = DpOffset(0.dp, 4.dp),
                onDismissRequest = { isDropDownShown = false },
                modifier = Modifier
                    .width(maxWidth)
                    .heightIn(max = 200.dp)
                    .clip(shape)
                    .border(
                        border = BorderStroke(1.dp, LocalAppTheme.colors.stroke2),
                        shape = shape
                    ),
                content = {
                    Column(
                        modifier = Modifier
                            .heightIn(max = 200.dp)
                            .background(LocalAppTheme.colors.white),
                    ){
                        LazyColumn{
                            itemsIndexed(countryCodes){ index, countryCode ->

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickableWithIndication(
                                            rippleColor = LocalAppTheme.colors.stroke2
                                        ) {
                                            onUpdateCountryCode?.invoke(countryCode)
                                            isDropDownShown = false
                                        }
                                        .padding(16.dp),
                                ) {
                                    Text(
                                        text = countryCode.code,
                                        style = LocalAppTheme.typography.l16,
                                        color = LocalAppTheme.colors.primaryText
                                    )
                                    Text(
                                        text = stringResource(id = countryCode.getTitleRes()),
                                        modifier = Modifier.weight(1f),
                                        style = LocalAppTheme.typography.l16,
                                        color = LocalAppTheme.colors.primaryText,
                                        textAlign = TextAlign.End,
                                    )
                                }
                                if (index != countryCodes.size - 1) {
                                    CommonDivider(
                                        color = LocalAppTheme.colors.stroke2
                                    )
                                }
                            }
                        }
                    }
                }
            )
        }
    }
}

private fun CountryCode.getTitleRes(): Int {
    return when (this) {
        CountryCode.KZ -> R.string.country_kz
        CountryCode.RU -> R.string.country_ru
        CountryCode.US -> R.string.country_us
        CountryCode.AE -> R.string.country_ae
        CountryCode.AF -> R.string.country_af
        CountryCode.AL -> R.string.country_al
        CountryCode.DZ -> R.string.country_dz
        CountryCode.AS -> R.string.country_as
        CountryCode.AD -> R.string.country_ad
        CountryCode.AO -> R.string.country_ao
        CountryCode.AI -> R.string.country_ai
        CountryCode.AQ -> R.string.country_aq
        CountryCode.AG -> R.string.country_ag
        CountryCode.AR -> R.string.country_ar
        CountryCode.AM -> R.string.country_am
        CountryCode.AW -> R.string.country_aw
        CountryCode.AU -> R.string.country_au
        CountryCode.AT -> R.string.country_at
        CountryCode.AZ -> R.string.country_az
        CountryCode.BS -> R.string.country_bs
        CountryCode.BH -> R.string.country_bh
        CountryCode.BD -> R.string.country_bd
        CountryCode.BB -> R.string.country_bb
        CountryCode.BY -> R.string.country_by
        CountryCode.BE -> R.string.country_be
        CountryCode.BZ -> R.string.country_bz
        CountryCode.BJ -> R.string.country_bj
        CountryCode.BM -> R.string.country_bm
        CountryCode.BT -> R.string.country_bt
        CountryCode.BO -> R.string.country_bo
        CountryCode.BA -> R.string.country_ba
        CountryCode.BW -> R.string.country_bw
        CountryCode.BR -> R.string.country_br
        CountryCode.BN -> R.string.country_bn
        CountryCode.BG -> R.string.country_bg
        CountryCode.BF -> R.string.country_bf
        CountryCode.BI -> R.string.country_bi
        CountryCode.KH -> R.string.country_kh
        CountryCode.CM -> R.string.country_cm
        CountryCode.CA -> R.string.country_ca
        CountryCode.CV -> R.string.country_cv
        CountryCode.KY -> R.string.country_ky
        CountryCode.CF -> R.string.country_cf
        CountryCode.TD -> R.string.country_td
        CountryCode.CL -> R.string.country_cl
        CountryCode.CN -> R.string.country_cn
        CountryCode.CX -> R.string.country_cx
        CountryCode.CC -> R.string.country_cc
        CountryCode.CO -> R.string.country_co
        CountryCode.KM -> R.string.country_km
        CountryCode.CD -> R.string.country_cd
        CountryCode.CG -> R.string.country_cg
        CountryCode.CK -> R.string.country_ck
        CountryCode.CR -> R.string.country_cr
        CountryCode.CI -> R.string.country_ci
        CountryCode.HR -> R.string.country_hr
        CountryCode.CU -> R.string.country_cu
        CountryCode.CY -> R.string.country_cy
        CountryCode.CZ -> R.string.country_cz
        CountryCode.DK -> R.string.country_dk
        CountryCode.DJ -> R.string.country_dj
        CountryCode.DM -> R.string.country_dm
        CountryCode.DO -> R.string.country_do
        CountryCode.EC -> R.string.country_ec
        CountryCode.EG -> R.string.country_eg
        CountryCode.SV -> R.string.country_sv
        CountryCode.GQ -> R.string.country_gq
        CountryCode.ER -> R.string.country_er
        CountryCode.EE -> R.string.country_ee
        CountryCode.ET -> R.string.country_et
        CountryCode.FK -> R.string.country_fk
        CountryCode.FO -> R.string.country_fo
        CountryCode.FJ -> R.string.country_fj
        CountryCode.FI -> R.string.country_fi
        CountryCode.FR -> R.string.country_fr
        CountryCode.GF -> R.string.country_gf
        CountryCode.PF -> R.string.country_pf
        CountryCode.GA -> R.string.country_ga
        CountryCode.GM -> R.string.country_gm
        CountryCode.GE -> R.string.country_ge
        CountryCode.DE -> R.string.country_de
        CountryCode.GH -> R.string.country_gh
        CountryCode.GI -> R.string.country_gi
        CountryCode.GR -> R.string.country_gr
        CountryCode.GL -> R.string.country_gl
        CountryCode.GD -> R.string.country_gd
        CountryCode.GP -> R.string.country_gp
        CountryCode.GU -> R.string.country_gu
        CountryCode.GT -> R.string.country_gt
        CountryCode.GN -> R.string.country_gn
        CountryCode.GW -> R.string.country_gw
        CountryCode.GY -> R.string.country_gy
        CountryCode.HT -> R.string.country_ht
        CountryCode.HN -> R.string.country_hn
        CountryCode.HK -> R.string.country_hk
        CountryCode.HU -> R.string.country_hu
        CountryCode.IS -> R.string.country_is
        CountryCode.IN -> R.string.country_in
        CountryCode.ID -> R.string.country_id
        CountryCode.IR -> R.string.country_ir
        CountryCode.IQ -> R.string.country_iq
        CountryCode.IE -> R.string.country_ie
        CountryCode.IL -> R.string.country_il
        CountryCode.IT -> R.string.country_it
        CountryCode.JM -> R.string.country_jm
        CountryCode.JP -> R.string.country_jp
        CountryCode.JO -> R.string.country_jo
        CountryCode.KE -> R.string.country_ke
        CountryCode.KI -> R.string.country_ki
        CountryCode.KP -> R.string.country_kp
        CountryCode.KR -> R.string.country_kr
        CountryCode.KW -> R.string.country_kw
        CountryCode.KG -> R.string.country_kg
        CountryCode.LA -> R.string.country_la
        CountryCode.LV -> R.string.country_lv
        CountryCode.LB -> R.string.country_lb
        CountryCode.LS -> R.string.country_ls
        CountryCode.LR -> R.string.country_lr
        CountryCode.LY -> R.string.country_ly
        CountryCode.LI -> R.string.country_li
        CountryCode.LT -> R.string.country_lt
        CountryCode.LU -> R.string.country_lu
        CountryCode.MO -> R.string.country_mo
        CountryCode.MK -> R.string.country_mk
        CountryCode.MG -> R.string.country_mg
        CountryCode.MW -> R.string.country_mw
        CountryCode.MY -> R.string.country_my
        CountryCode.MV -> R.string.country_mv
        CountryCode.ML -> R.string.country_ml
        CountryCode.MT -> R.string.country_mt
        CountryCode.MH -> R.string.country_mh
        CountryCode.MQ -> R.string.country_mq
        CountryCode.MR -> R.string.country_mr
        CountryCode.MU -> R.string.country_mu
        CountryCode.YT -> R.string.country_yt
        CountryCode.MX -> R.string.country_mx
        CountryCode.FM -> R.string.country_fm
        CountryCode.MD -> R.string.country_md
        CountryCode.MC -> R.string.country_mc
        CountryCode.MN -> R.string.country_mn
        CountryCode.MS -> R.string.country_ms
        CountryCode.MA -> R.string.country_ma
        CountryCode.MZ -> R.string.country_mz
        CountryCode.MM -> R.string.country_mm
        CountryCode.NA -> R.string.country_na
        CountryCode.NR -> R.string.country_nr
        CountryCode.NP -> R.string.country_np
        CountryCode.NL -> R.string.country_nl
        CountryCode.AN -> R.string.country_an
        CountryCode.NC -> R.string.country_nc
        CountryCode.NZ -> R.string.country_nz
        CountryCode.NI -> R.string.country_ni
        CountryCode.NE -> R.string.country_ne
        CountryCode.NG -> R.string.country_ng
        CountryCode.NU -> R.string.country_nu
        CountryCode.NF -> R.string.country_nf
        CountryCode.MP -> R.string.country_mp
        CountryCode.NO -> R.string.country_no
        CountryCode.OM -> R.string.country_om
        CountryCode.PK -> R.string.country_pk
        CountryCode.PW -> R.string.country_pw
        CountryCode.PS -> R.string.country_ps
        CountryCode.PA -> R.string.country_pa
        CountryCode.PG -> R.string.country_pg
        CountryCode.PY -> R.string.country_py
        CountryCode.PE -> R.string.country_pe
        CountryCode.PH -> R.string.country_ph
        CountryCode.PN -> R.string.country_pn
        CountryCode.PL -> R.string.country_pl
        CountryCode.PT -> R.string.country_pt
        CountryCode.PR -> R.string.country_pr
        CountryCode.QA -> R.string.country_qa
        CountryCode.RE -> R.string.country_re
        CountryCode.RO -> R.string.country_ro
        CountryCode.RW -> R.string.country_rw
        CountryCode.SH -> R.string.country_sh
        CountryCode.KN -> R.string.country_kn
        CountryCode.LC -> R.string.country_lc
        CountryCode.PM -> R.string.country_pm
        CountryCode.VC -> R.string.country_vc
        CountryCode.WS -> R.string.country_ws
        CountryCode.SM -> R.string.country_sm
        CountryCode.ST -> R.string.country_st
        CountryCode.SA -> R.string.country_sa
        CountryCode.SN -> R.string.country_sn
        CountryCode.SC -> R.string.country_sc
        CountryCode.SL -> R.string.country_sl
        CountryCode.SG -> R.string.country_sg
        CountryCode.SK -> R.string.country_sk
        CountryCode.SI -> R.string.country_si
        CountryCode.SB -> R.string.country_sb
        CountryCode.SO -> R.string.country_so
        CountryCode.ZA -> R.string.country_za
        CountryCode.ES -> R.string.country_es
        CountryCode.LK -> R.string.country_lk
        CountryCode.SD -> R.string.country_sd
        CountryCode.SR -> R.string.country_sr
        CountryCode.SZ -> R.string.country_sz
        CountryCode.SE -> R.string.country_se
        CountryCode.CH -> R.string.country_ch
        CountryCode.SY -> R.string.country_sy
        CountryCode.TW -> R.string.country_tw
        CountryCode.TJ -> R.string.country_tj
        CountryCode.TZ -> R.string.country_tz
        CountryCode.TH -> R.string.country_th
        CountryCode.TG -> R.string.country_tg
        CountryCode.TK -> R.string.country_tk
        CountryCode.TO -> R.string.country_to
        CountryCode.TT -> R.string.country_tt
        CountryCode.TN -> R.string.country_tn
        CountryCode.TR -> R.string.country_tr
        CountryCode.TM -> R.string.country_tm
        CountryCode.TC -> R.string.country_tc
        CountryCode.TV -> R.string.country_tv
        CountryCode.UG -> R.string.country_ug
        CountryCode.UA -> R.string.country_ua
        CountryCode.GB -> R.string.country_gb
        CountryCode.UY -> R.string.country_uy
        CountryCode.UZ -> R.string.country_uz
        CountryCode.VU -> R.string.country_vu
        CountryCode.VA -> R.string.country_va
        CountryCode.VE -> R.string.country_ve
        CountryCode.VN -> R.string.country_vn
        CountryCode.VG -> R.string.country_vg
        CountryCode.VI -> R.string.country_vi
        CountryCode.WF -> R.string.country_wf
        CountryCode.YE -> R.string.country_ye
        CountryCode.YU -> R.string.country_yu
        CountryCode.ZR -> R.string.country_zr
        CountryCode.ZM -> R.string.country_zm
        CountryCode.ZW -> R.string.country_zw
    }
}