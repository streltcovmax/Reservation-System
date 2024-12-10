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
            //TODO проверка на NULL?
            //TODO без цикла?

            boolean isBusy = false;
            for (Long orderId: table.getOrdersId()){
                isBusy = orderRepo.findOrderDataById(orderId).getDateTime().getDayOfYear() == dateTime.getDayOfYear();
                if(isBusy){
                    log.info(table.getId() + "is busy");
                    break;
                }
                log.info(table.getId() + "is NOT busy");
            }
            if(!isBusy){
                return table.getId();
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
