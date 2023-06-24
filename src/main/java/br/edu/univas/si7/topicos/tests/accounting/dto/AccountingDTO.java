package br.edu.univas.si7.topicos.tests.accounting.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountingDTO {
	private long itemCode;
	private String type;
	private String description;
	private float accValue;
	private float profit;
	private String dateTime;
//	private boolean active;
}
