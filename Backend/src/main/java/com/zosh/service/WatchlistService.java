package com.zosh.service;

import com.zosh.model.Coin;
import com.zosh.model.Appuser;
import com.zosh.model.Watchlist;

public interface WatchlistService {

    Watchlist findUserWatchlist(Long userId) throws Exception;

    Watchlist createWatchList(Appuser appuser);

    Watchlist findById(Long id) throws Exception;

    Coin addItemToWatchlist(Coin coin, Appuser appuser) throws Exception;
}
