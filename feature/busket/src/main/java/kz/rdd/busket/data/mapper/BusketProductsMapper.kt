package kz.rdd.busket.data.mapper

import kz.rdd.busket.data.model.BusketProductsDto
import kz.rdd.busket.domain.model.BusketProducts

class BusketProductsMapper {
    fun map(dto: BusketProductsDto) : BusketProducts {
        return BusketProducts(
            id = dto.id,
            quantity = dto.quantity,
            order = dto.order,
            food = dto.food,
            productInfo = BusketProducts.ProductInfo(
                id = dto.productInfo.id,
                name = dto.productInfo.name,
                price = dto.productInfo.price,
                ingredients = dto.productInfo.ingredients,
                cuisine = dto.productInfo.cuisine,
                taste = dto.productInfo.taste,
                grade = dto.productInfo.grade,
                photo = dto.productInfo.photo,
                user = dto.productInfo.user,
                username = dto.productInfo.username
            )
        )
    }
}