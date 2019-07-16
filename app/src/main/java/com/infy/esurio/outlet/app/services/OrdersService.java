package com.infy.esurio.outlet.app.services;

import android.widget.TextView;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.infy.esurio.outlet.app.This;
import com.infy.esurio.middleware.DTO.OrdersDTO;

import org.apache.commons.lang3.text.WordUtils;

import java.util.List;


public class OrdersService {

    private static class FakerService {


        public static ObservableList<OrdersDTO> outletsFetch() {
            ObservableList<OrdersDTO> list = new ObservableArrayList<>();
            OrdersDTO dto = null;
            dto = new OrdersDTO();
            dto.setIdentifier("#5");
            list.add(dto);

            dto = new OrdersDTO();
            dto.setIdentifier("#4");
            list.add(dto);

            dto = new OrdersDTO();
            dto.setIdentifier("#3");
            list.add(dto);

            dto = new OrdersDTO();
            dto.setIdentifier("#2");
            list.add(dto);

            dto = new OrdersDTO();
            dto.setIdentifier("#1");
            list.add(dto);


            return list;
        }

        public static void putNUmber(OrdersDTO dto, TextView view) {
            view.setText(WordUtils.capitalizeFully(dto.getIdentifier()));
        }

        public static void putServings(OrdersDTO dto, List<String> servings) {
            servings.clear();
            switch (dto.getIdentifier()) {
                case "#1":
                    servings.add("masala dosa - 2");
                    break;
                case "#2":
                    servings.add("egg friedrice - 1");
                    servings.add("paneer chilli - 1");
                    break;

                case "#3":
                    servings.add("chicken biriyani - 1");
                    break;
                case "#4":
                    servings.add("mixed noodles - 1");
                    break;
                case "#5":
                    servings.add("masala dosa - 1");
                    break;
            }
        }
    }

    public static ObservableList<OrdersDTO> fetch() {
        This.ORDERS.clear();
        This.ORDERS.addAll(FakerService.outletsFetch());
        return This.ORDERS;
    }

    public static void putNumber(OrdersDTO dto, TextView view) {
        FakerService.putNUmber(dto, view);
    }

    public static void putServings(OrdersDTO dto, List<String> servings) {
        FakerService.putServings(dto, servings);
    }


}
