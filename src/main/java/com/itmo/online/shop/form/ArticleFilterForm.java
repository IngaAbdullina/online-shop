package com.itmo.online.shop.form;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ArticleFilterForm {
	
	private List<String> size;
	private List<String> category;
	private List<String> brand;
	private Integer priceLow;
	private Integer priceHigh;
	private String sort;
	private Integer page;
	private String search;
}
