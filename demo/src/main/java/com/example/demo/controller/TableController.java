package com.example.demo.controller;

import com.example.demo.model.TableReservationData;
import com.example.demo.service.TableService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Slf4j
@Controller
@RequiredArgsConstructor
public class TableController {

    private final TableService tableService;

    @GetMapping("/")
    public String redirectToIndex() {
        return "index"; // Файл index.html будет использоваться как начальная страница
    }
    
    @GetMapping("/tables")
    public String showTableForm(HttpSession session, Model model) {
        TableReservationData tableData = new TableReservationData();
        TableReservationData sessionTableData = (TableReservationData) session.getAttribute("tableData");
        if(sessionTableData != null){
            tableData = sessionTableData;
        }
        model.addAttribute("tableData", tableData);
        return "tables"; // Файл tables.html
    }


    @PostMapping("/tables")
    public String submitTableForm(HttpSession session, TableReservationData tableData){
        tableData.setTableNumber((Integer) session.getAttribute("tableNumber"));
        session.setAttribute("tableData", tableData);
        return "redirect:/reservation";
    }

    @PostMapping("/tables/find")
    public ResponseEntity<Integer> findTables(HttpSession session, @RequestBody TableReservationData data){
        int tableNumber = tableService.findTable(data.getDateTime(), data.getNumberOfPeople());
        session.setAttribute("tableNumber", tableNumber);
        return ResponseEntity.ok(tableNumber);
    }
}

/*TODO
   во всех контроллерах на гет маппингах прописать проверки чтобы нельзя было вписав вручную адреса заходить на страницы

 */