package cc.ithinkyou.memcached;

import net.spy.memcached.ConnectionFactory;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class MemcachedAutoConfiguration {

    private static final String LOCALHOST = "localhost";

    private static final int DEFAULT_PORT = 11211;

    @Autowired
    private Environment environment;

    @Bean
    public MemcachedClient memcachedClient(ObjectProvider<ConnectionFactory> connection) throws IOException {
        final List<InetSocketAddress> addresses = new ArrayList<>();
        final String servers = environment.getProperty("memcached.servers");
        if (StringUtils.isEmpty(servers)) {
            addresses.add(new InetSocketAddress(LOCALHOST, DEFAULT_PORT));
        } else {
            for (final String server : servers.split(",")) {
                final int colon = server.indexOf(":");
                if (colon == -1) {
                    addresses.add(new InetSocketAddress(server, DEFAULT_PORT));
                } else {
                    final int port = Integer.parseInt(server.substring(colon + 1));
                    addresses.add(new InetSocketAddress(server.substring(0, colon), port));
                }
            }
        }

        ConnectionFactory con = connection.getIfUnique();

        return con == null
                ? new MemcachedClient(addresses)
                : new MemcachedClient(con, addresses);
    }
}
