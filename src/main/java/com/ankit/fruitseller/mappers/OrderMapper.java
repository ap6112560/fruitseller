package com.ankit.fruitseller.mappers;

import com.ankit.fruitseller.models.Order;
import com.ankit.fruitseller.models.projections.OrderView;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    List<OrderView> map(List<Order> orders);

    OrderView map(Order value);
}
