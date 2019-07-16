package com.infy.esurio.outlet.app.services;

import android.graphics.Color;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.infy.esurio.R;
import com.infy.esurio.outlet.app.This;
import com.infy.esurio.middleware.DTO.OrdersDTO;

import org.apache.commons.lang3.text.WordUtils;

import static com.infy.esurio.outlet.app.This.COLORS.GREEN;
import static com.infy.esurio.outlet.app.This.COLORS.ORANGE;
import static com.infy.esurio.outlet.app.This.COLORS.RED;


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
    }

    public static ObservableList<OrdersDTO> fetch() {
        This.OUTLETS.clear();
        This.OUTLETS.addAll(FakerService.outletsFetch());
        return This.OUTLETS;
    }

    public static void putNumber(OrdersDTO dto, TextView view) {
        FakerService.putNUmber(dto, view);
    }



}
