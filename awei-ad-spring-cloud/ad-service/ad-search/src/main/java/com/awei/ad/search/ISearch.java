package com.awei.ad.search;


import com.awei.ad.search.vo.SearchRequest;
import com.awei.ad.search.vo.SearchResponse;

/**
 * @description:
 * @author: PENGLW
 * @date: 2020/11/12
 */
public interface ISearch {

    SearchResponse fetchAds(SearchRequest request);
}
