package kz.rdd.busket.data.mapper

import kz.rdd.busket.data.model.BusketListDto
import kz.rdd.busket.domain.model.BusketList

class BusketListMapper {
    fun map(dto: BusketListDto) : BusketList {
        return BusketList(
            id = dto.id,
            status = dto.status,
            dateOrdered = dto.dateOrdered,
            totalPrice = dto.totalPrice,
            customer = BusketList.Customer(
                id = dto.customer.id,
                email = dto.customer.email,
                firstName = dto.customer.firstName,
                lastName = dto.customer.lastName,
                address = dto.customer.address,
            )
        )
    }
}