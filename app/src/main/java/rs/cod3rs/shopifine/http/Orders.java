package rs.cod3rs.shopifine.http;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.api.RestClientErrorHandling;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import rs.cod3rs.shopifine.Config;
import rs.cod3rs.shopifine.hateoas.bill_items.BillItemCollectionResponse;
import rs.cod3rs.shopifine.hateoas.bill_items.BillItemRequest;
import rs.cod3rs.shopifine.hateoas.bill_items.BillItemResponse;
import rs.cod3rs.shopifine.hateoas.bills.BillCollectionResponse;
import rs.cod3rs.shopifine.hateoas.bills.BillRequest;
import rs.cod3rs.shopifine.hateoas.bills.BillResponse;
import rs.cod3rs.shopifine.hateoas.discounts.DiscountCollectionResponse;

@Rest(
        rootUrl = Config.SERVICE_URL,
        converters = MappingJackson2HttpMessageConverter.class,
        interceptors = AuthInterceptor.class,
        responseErrorHandler = HttpErrorHandler.class
)
public interface Orders extends RestClientErrorHandling {

    @Get("api/users/{userId}/bills?filter[status]={orderStatus}")
    BillCollectionResponse getBills(@Path final Integer userId, @Path final String orderStatus);

    @Post("api/users/{userId}/bills")
    BillResponse createBill(@Path final Integer userId, @Body final BillRequest request);

    @Get("api/users/{userId}/bills/{billId}/bill-items")
    BillItemCollectionResponse getBillItems(@Path final Integer userId, @Path final Long billId);

    @Post("api/users/{userId}/bills/{billId}/bill-items")
    BillItemResponse addBillItem(@Path final Integer userId, @Path Integer billId, @Body BillItemRequest request);

    @Get("api/users/{userId}/bills/{billId}/bill-items/{billItemId}/discounts")
    DiscountCollectionResponse getBillItemDiscounts(@Path final Integer userId, @Path final Long billId, @Path final Long billItemId);

    @Get("api/users/{userId}/bills/{billId}/discounts")
    DiscountCollectionResponse getBillDiscounts(@Path final Integer userId, @Path final Long billId);

}
