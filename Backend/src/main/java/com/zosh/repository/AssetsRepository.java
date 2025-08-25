package com.zosh.repository;

import com.zosh.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssetsRepository extends JpaRepository<Asset, Long> {
   public List<Asset> findByAppuserId(Long userId);

   Asset findByAppuserIdAndCoinId(Long userId, String coinId);

   Asset findByIdAndAppuserId(Long assetId, Long userId);

   // Optional<Assets> findByAppuserIdAndSymbolAndPortfolioId(Long userId,String
   // symbol, Long portfolioId);
}
