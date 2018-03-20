package info.matsumana.sample.webfiltersample.filter;

import org.reactivestreams.Publisher;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SampleFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange).compose(this::filter);
    }

    private Publisher<Void> filter(Mono<Void> call) {
        System.out.println("start");

        long start = System.currentTimeMillis();
        return call.doOnSuccess(done -> success(start))
                   .doOnError(cause -> error(start))
                   .doOnSuccessOrError((done, cause) -> successOrError(start, cause));
    }

    private void success(long start) {
        long end = System.currentTimeMillis();

        System.out.println("success " + (end - start));
    }

    private void error(long start) {
        long end = System.currentTimeMillis();

        System.out.println("error " + (end - start));
    }

    private void successOrError(long start, Throwable cause) {
        long end = System.currentTimeMillis();

        if (cause == null) {
            System.out.println("success");
        } else {
            System.out.println("error");
        }

        System.out.println("finished " + (end - start));
    }
}
