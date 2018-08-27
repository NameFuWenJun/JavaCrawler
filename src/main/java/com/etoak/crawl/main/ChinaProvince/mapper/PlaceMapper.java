package com.etoak.crawl.main.ChinaProvince.mapper;

import com.etoak.crawl.main.ChinaProvince.pojo.Place;

public interface PlaceMapper {
    public void insertPlace(Place place);
    public Place getPlace(String ID);
    
}
