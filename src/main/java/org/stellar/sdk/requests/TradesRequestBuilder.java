package org.stellar.sdk.requests;

import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Request;
import org.stellar.sdk.Asset;
import org.stellar.sdk.AssetTypeCreditAlphaNum;
import org.stellar.sdk.responses.Page;
import org.stellar.sdk.responses.TradeResponse;

import java.io.IOException;
import java.net.URI;

/**
 * Builds requests connected to trades.
 */
public class TradesRequestBuilder extends RequestBuilder {
    public TradesRequestBuilder(URI serverURI) {
        super(serverURI, "trades");
    }

    public TradesRequestBuilder baseAsset(Asset asset) {
        uriBuilder.addParameter("base_asset_type", asset.getType());
        if (asset instanceof AssetTypeCreditAlphaNum) {
            AssetTypeCreditAlphaNum creditAlphaNumAsset = (AssetTypeCreditAlphaNum) asset;
            uriBuilder.addParameter("base_asset_code", creditAlphaNumAsset.getCode());
            uriBuilder.addParameter("base_asset_issuer", creditAlphaNumAsset.getIssuer().getAccountId());
        }
        return this;
    }

    public TradesRequestBuilder counterAsset(Asset asset) {
        uriBuilder.addParameter("counter_asset_type", asset.getType());
        if (asset instanceof AssetTypeCreditAlphaNum) {
            AssetTypeCreditAlphaNum creditAlphaNumAsset = (AssetTypeCreditAlphaNum) asset;
            uriBuilder.addParameter("counter_asset_code", creditAlphaNumAsset.getCode());
            uriBuilder.addParameter("counter_asset_issuer", creditAlphaNumAsset.getIssuer().getAccountId());
        }
        return this;
    }

    public TradesRequestBuilder offerId(String offerId) {
        uriBuilder.addParameter("offer_id", offerId);
        return this;
    }

    public static Page<TradeResponse> execute(URI uri) throws IOException, TooManyRequestsException {
        TypeToken type = new TypeToken<Page<TradeResponse>>() {};
        ResponseHandler<Page<TradeResponse>> responseHandler = new ResponseHandler<Page<TradeResponse>>(type);
        return (Page<TradeResponse>) Request.Get(uri).execute().handleResponse(responseHandler);
    }

    public Page<TradeResponse> execute() throws IOException, TooManyRequestsException {
        return this.execute(this.buildUri());
    }
}
