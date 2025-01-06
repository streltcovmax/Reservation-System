package com.example.demo.service;

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
                        isBusy = orderRepo.findOrderDataById(orderId).getDate() == dateTime.toLocalDate();
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
}
