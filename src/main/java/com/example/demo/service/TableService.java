package com.example.demo.service;

import com.example.demo.model.OrderData;
import com.example.demo.model.Table;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.TableRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TableService {
    private final TableRepository tableRepo;
    private final OrderRepository orderRepo;

    public int findTable(LocalDateTime dateTime, int amountOfPeople){
        ArrayList<Table> capableTables = tableRepo.findAllByCapacityIsGreaterThanEqual(amountOfPeople);

        log.info(capableTables.toString());

        for (Table table: capableTables){

            boolean isBusy = false;
            try {
                for (Long orderId : table.getOrdersId()) {
                    log.info("current id to check is " + orderId);
                    try {
                        isBusy = orderRepo.findOrderDataById(orderId).getDate().toString().equals(dateTime.toLocalDate().toString());
                        log.info(orderRepo.findOrderDataById(orderId).getDate().toString() + " compared to " + dateTime.toLocalDate().toString());
                        log.info("EQUALS: " +  isBusy);
                        if (isBusy) {
                            log.info(table.getId() + "is busy");
                            break;
                        }
                        log.info(table.getId() + "is NOT busy");
                    } catch (NullPointerException exception) {
                        log.error("К столу " + table.getId() + " прикреплен несуществующий заказ " + orderId);
                    }
                }
                if (!isBusy) {
                    return table.getId();
                }
            }catch (NullPointerException exception){
                log.warn("Стол " + table.getId() + " записан в БД некорректно");
            }
        }
        return -1;
    }

    public void addOrderToTable(long orderId, int tableId){

        log.info(String.valueOf(tableId));
        log.info(String.valueOf(tableRepo.findById(tableId)));

        Table table = tableRepo.findById(tableId);
        List<Long> ordersList = new ArrayList<>(Arrays.asList(table.getOrdersId()));
        ordersList.add(orderId);
        table.setOrdersId(ordersList.toArray(new Long[0]));
        tableRepo.save(table);
    }

    public void deleteOrderFromTable(long orderId){
        int tableId = orderRepo.findOrderDataById(orderId).getTableNumber();
        Table table = tableRepo.findById(tableId);
        ArrayList<Long> ordersId = new ArrayList<>(List.of(table.getOrdersId()));
        ordersId.remove(orderId);
        Long[] newOrdersId = new Long[ordersId.size()];
        ordersId.toArray(newOrdersId);
        table.setOrdersId(newOrdersId);
        tableRepo.save(table);
    }
}
