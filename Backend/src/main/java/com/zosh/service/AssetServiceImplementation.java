package com.zosh.service;

import com.zosh.model.Asset;
import com.zosh.model.Coin;
import com.zosh.model.Appuser;
import com.zosh.repository.AssetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssetServiceImplementation implements AssetService {
    private final AssetsRepository assetRepository;

    @Autowired
    public AssetServiceImplementation(AssetsRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    @Override
    public Asset createAsset(Appuser appuser, Coin coin, double quantity) {
        Asset asset = new Asset();

        asset.setQuantity(quantity);
        asset.setBuyPrice(coin.getCurrentPrice());
        asset.setCoin(coin);
        asset.setAppuser(appuser);

        return assetRepository.save(asset);
    }

    // public Asset buyAsset(Appuser appuser, Coin coin, Long quantity) {
    // return createAsset(appuser,coin,quantity);
    // }

    public Asset getAssetById(Long assetId) {
        return assetRepository.findById(assetId)
                .orElseThrow(() -> new IllegalArgumentException("Asset not found"));
    }

    @Override
    public Asset getAssetByUserAndId(Long userId, Long assetId) {
        return assetRepository.findByIdAndAppuserId(assetId, userId);
    }

    @Override
    public List<Asset> getUsersAssets(Long userId) {
        return assetRepository.findByAppuserId(userId);
    }

    @Override
    public Asset updateAsset(Long assetId, double quantity) throws Exception {

        Asset oldAsset = getAssetById(assetId);
        if (oldAsset == null) {
            throw new Exception("Asset not found...");
        }
        oldAsset.setQuantity(quantity + oldAsset.getQuantity());

        return assetRepository.save(oldAsset);
    }

    @Override
    public Asset findAssetByAppuserIdAndCoinId(Long userId, String coinId) throws Exception {
        return assetRepository.findByAppuserIdAndCoinId(userId, coinId);
    }

    public void deleteAsset(Long assetId) {
        assetRepository.deleteById(assetId);
    }

}
