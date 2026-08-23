package haptixgolf.sports.haptixsportkit.data.model

import androidx.annotation.StringRes
import haptixgolf.sports.haptixsportkit.R

enum class ProductCategory(@field:StringRes val titleRes: Int) {
    GOLF(R.string.yjijw_category_golf),
    RACKET_SPORTS(R.string.yjijw_category_racket),
    FITNESS(R.string.yjijw_category_fitness),
    TRAINING(R.string.yjijw_category_training),
    APPAREL(R.string.yjijw_category_apparel)
}
