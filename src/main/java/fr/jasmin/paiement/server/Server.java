package fr.jasmin.paiement.server;

import java.nio.file.Paths;
//import static spark.Spark.post;
//import static spark.Spark.port;
//import static spark.Spark.staticFiles;
//
//import com.stripe.Stripe;
//import com.stripe.model.checkout.Session;
//import com.stripe.param.checkout.SessionCreateParams;

public class Server {
//
//  public static void main(String[] args) {
//    port(4242);
//
//    // This is your test secret API key.
//    Stripe.apiKey = "pk_test_51Nw0nIDXYCPnDTVIViHil3ysW5J9C4gAi3s7r7UkAvESqvu2z5ULYjXKkdvhMr33CaJDQ7sA6zCfBDO5U3hkzBHc00N6vJNRnG";
//
//    staticFiles.externalLocation(
//        Paths.get("public").toAbsolutePath().toString());
//
//    post("/create-checkout-session", (request, response) -> {
//        String YOUR_DOMAIN = "http://localhost:4242";
//        SessionCreateParams params =
//          SessionCreateParams.builder()
//            .setMode(SessionCreateParams.Mode.PAYMENT)
//            .setSuccessUrl(YOUR_DOMAIN + "/success.html")
//            .setCancelUrl(YOUR_DOMAIN + "/cancel.html")
//            .addLineItem(
//              SessionCreateParams.LineItem.builder()
//                .setQuantity(1L)
//                // Provide the exact Price ID (for example, pr_1234) of the product you want to sell
//                .setPrice("150")
//                .setPriceData(
//                        SessionCreateParams.LineItem.PriceData.builder()
//                          .setCurrency("EUR")
//                          .setUnitAmount(2000L)
//                          .setProductData(
//                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
//                              .setName("T-shirt")
//                              .build())
//                          .build())
//                      .build())
//                    .build();
//      Session session = Session.create(params);
//
//      response.redirect(session.getUrl(), 303);
//      return "";
//    });
//  }
}