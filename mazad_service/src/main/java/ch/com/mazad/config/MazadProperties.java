package ch.com.mazad.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;

import javax.validation.constraints.NotNull;

@ConfigurationProperties(prefix = "mazad", ignoreUnknownFields = true)
@Component
public class MazadProperties {
    private final Async async = new Async();

    private final Http http = new Http();

    private final Sms sms = new Sms();

    private final Cache cache = new Cache();

    private final Mail mail = new Mail();

    private final Security security = new Security();

    private final Swagger swagger = new Swagger();

    private final Rabbit rabbit = new Rabbit();


    private final Merged merged = new Merged();

    private final CorsConfiguration cors = new CorsConfiguration();

    private final Photo photo = new Photo();

    public Async getAsync() {
        return async;
    }

    public Http getHttp() {
        return http;
    }

    public Sms getSms() {
        return sms;
    }

    public Cache getCache() {
        return cache;
    }

    public Mail getMail() {
        return mail;
    }

    public Security getSecurity() {
        return security;
    }

    public Swagger getSwagger() {
        return swagger;
    }


    public CorsConfiguration getCors() {
        return cors;
    }

    public Merged getMerged() {
        return merged;
    }

    public Rabbit getRabbit() {
        return rabbit;
    }

    public Photo getPhoto() {
        return photo;
    }

    public static class Async {

        private int corePoolSize = 2;

        private int maxPoolSize = 50;

        private int queueCapacity = 10000;

        public int getCorePoolSize() {
            return corePoolSize;
        }

        public void setCorePoolSize(int corePoolSize) {
            this.corePoolSize = corePoolSize;
        }

        public int getMaxPoolSize() {
            return maxPoolSize;
        }

        public void setMaxPoolSize(int maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
        }

        public int getQueueCapacity() {
            return queueCapacity;
        }

        public void setQueueCapacity(int queueCapacity) {
            this.queueCapacity = queueCapacity;
        }
    }

    public static class Http {

        private final Cache cache = new Cache();

        public Cache getCache() {
            return cache;
        }

        public static class Cache {

            private int timeToLiveInDays = 1461;

            public int getTimeToLiveInDays() {
                return timeToLiveInDays;
            }

            public void setTimeToLiveInDays(int timeToLiveInDays) {
                this.timeToLiveInDays = timeToLiveInDays;
            }
        }
    }

    public static class Cache {

        private int timeToLiveSeconds = 3600;

        private final Ehcache ehcache = new Ehcache();

        public int getTimeToLiveSeconds() {
            return timeToLiveSeconds;
        }

        public void setTimeToLiveSeconds(int timeToLiveSeconds) {
            this.timeToLiveSeconds = timeToLiveSeconds;
        }

        public Ehcache getEhcache() {
            return ehcache;
        }

        public static class Ehcache {

            private String maxBytesLocalHeap = "16M";

            public String getMaxBytesLocalHeap() {
                return maxBytesLocalHeap;
            }

            public void setMaxBytesLocalHeap(String maxBytesLocalHeap) {
                this.maxBytesLocalHeap = maxBytesLocalHeap;
            }
        }
    }

    public static class Sms {

        private final Twilio twilio = new Twilio();

        public Twilio getTwilio() {
            return twilio;
        }

        public static class Twilio {

            private String accountSid;

            private String authToken;

            public String getAccountSid() {
                return accountSid;
            }

            public void setAccountSid(String accountSid) {
                this.accountSid = accountSid;
            }

            public String getAuthToken() {
                return authToken;
            }

            public void setAuthToken(String authToken) {
                this.authToken = authToken;
            }
        }

    }


    public static class Mail {

        private String from = "Admin@agenda.ch";

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }
    }

    public static class Security {

        private final RememberMe rememberMe = new RememberMe();

        public RememberMe getRememberMe() {
            return rememberMe;
        }

        public static class RememberMe {

            @NotNull
            private String key;

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }
        }
    }

    public static class Swagger {

        private String title /*= "agenda service API"*/;

        private String description /*= "agenda service API documentation"*/;

        private String version /*= "0.0.1"*/;

        private String termsOfServiceUrl;

        private String contactName;

        private String contactUrl;

        private String contactEmail;

        private String license /*= "Apache 2.0"*/;

        private String licenseUrl /*= "http://www.apache.org/licenses/LICENSE-2.0"*/;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getTermsOfServiceUrl() {
            return termsOfServiceUrl;
        }

        public void setTermsOfServiceUrl(String termsOfServiceUrl) {
            this.termsOfServiceUrl = termsOfServiceUrl;
        }

        public String getContactName() {
            return contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        public String getContactUrl() {
            return contactUrl;
        }

        public void setContactUrl(String contactUrl) {
            this.contactUrl = contactUrl;
        }

        public String getContactEmail() {
            return contactEmail;
        }

        public void setContactEmail(String contactEmail) {
            this.contactEmail = contactEmail;
        }

        public String getLicense() {
            return license;
        }

        public void setLicense(String license) {
            this.license = license;
        }

        public String getLicenseUrl() {
            return licenseUrl;
        }

        public void setLicenseUrl(String licenseUrl) {
            this.licenseUrl = licenseUrl;
        }
    }


    private final Logging logging = new Logging();

    public Logging getLogging() {
        return logging;
    }

    public static class Logging {

        private final Logstash logstash = new Logstash();

        public Logstash getLogstash() {
            return logstash;
        }

        public static class Logstash {

            private boolean enabled = false;

            private String host = "localhost";

            private int port = 5000;

            private int queueSize = 512;

            public boolean isEnabled() {
                return enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }

            public String getHost() {
                return host;
            }

            public void setHost(String host) {
                this.host = host;
            }

            public int getPort() {
                return port;
            }

            public void setPort(int port) {
                this.port = port;
            }

            public int getQueueSize() {
                return queueSize;
            }

            public void setQueueSize(int queueSize) {
                this.queueSize = queueSize;
            }
        }
    }

    public static class Merged {

        private final Params params = new Params();

        public Params getParams() {
            return params;
        }


        public static class Params {

            private String code;

            private String note;

            private String openingHour;

            private String closureHour;

            private String tel;

            private String fax;

            private boolean state;

            private String label;

            private String bgColor;

            private int maxRdv;

            private String reservationType;

            private String street;

            private long canton;

            private long commune;

            private long npa;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public int getMaxRdv() {
                return maxRdv;
            }

            public void setMaxRdv(int maxRdv) {
                this.maxRdv = maxRdv;
            }

            public String getBgColor() {
                return bgColor;
            }

            public void setBgColor(String bgColor) {
                this.bgColor = bgColor;
            }

            public boolean isState() {
                return state;
            }

            public void setState(boolean state) {
                this.state = state;
            }

            public String getFax() {
                return fax;
            }

            public void setFax(String fax) {
                this.fax = fax;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public String getClosureHour() {
                return closureHour;
            }

            public void setClosureHour(String closureHour) {
                this.closureHour = closureHour;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public String getOpeningHour() {
                return openingHour;
            }

            public void setOpeningHour(String openingHour) {
                this.openingHour = openingHour;
            }

            public String getNote() {
                return note;
            }

            public void setNote(String note) {
                this.note = note;
            }

            public String getReservationType() {
                return reservationType;
            }

            public void setReservationType(String reservationType) {
                this.reservationType = reservationType;
            }

            public long getNpa() {
                return npa;
            }

            public void setNpa(long npa) {
                this.npa = npa;
            }

            public long getCommune() {
                return commune;
            }

            public void setCommune(long commune) {
                this.commune = commune;
            }

            public long getCanton() {
                return canton;
            }

            public void setCanton(long canton) {
                this.canton = canton;
            }

            public String getStreet() {
                return street;
            }

            public void setStreet(String street) {
                this.street = street;
            }
        }
    }


    public static class Rabbit {

        private String exchange;

        private String smsRoutingKey;

        private String mailRoutingKey;

        private String host;

        private String vhost;

        private String username;

        private String password;

        private int port;

        private int relayport;

        private String  smsSentQueue;
        private String  docEsSyncQueue;

        private String  docEsSyncRoutingKey;
        private String  smsSentRoutingKey;

        public String getSmsSentRoutingKey() {
            return smsSentRoutingKey;
        }

        public void setSmsSentRoutingKey(String smsSentRoutingKey) {
            this.smsSentRoutingKey = smsSentRoutingKey;
        }

        public String getExchange() {
            return exchange;
        }

        public void setExchange(String exchange) {
            this.exchange = exchange;
        }

        public String getSmsRoutingKey() {
            return smsRoutingKey;
        }

        public void setSmsRoutingKey(String smsRoutingKey) {
            this.smsRoutingKey = smsRoutingKey;
        }

        public String getMailRoutingKey() {
            return mailRoutingKey;
        }

        public void setMailRoutingKey(String mailRoutingKey) {
            this.mailRoutingKey = mailRoutingKey;
        }

        public String getSmsSentQueue() {
            return smsSentQueue;
        }

        public void setSmsSentQueue(String smsSentQueue) {
            this.smsSentQueue = smsSentQueue;
        }

        public String getDocEsSyncQueue() {
            return docEsSyncQueue;
        }

        public void setDocEsSyncQueue(String docEsSyncQueue) {
            this.docEsSyncQueue = docEsSyncQueue;
        }

        public String getDocEsSyncRoutingKey() {
            return docEsSyncRoutingKey;
        }

        public void setDocEsSyncRoutingKey(String docEsSyncRoutingKey) {
            this.docEsSyncRoutingKey = docEsSyncRoutingKey;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getVhost() {
            return vhost;
        }

        public void setVhost(String vhost) {
            this.vhost = vhost;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public int getRelayport() {
            return relayport;
        }

        public void setRelayport(int relayport) {
            this.relayport = relayport;
        }

    }

    public static class Photo {

        private String path;

        private String url;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
