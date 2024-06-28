import java.util.List;

public class Request {
    private String requestType;
    private String path;
    private String version;
    private String host;
    private String userAgent;
    private String accept;
    private String body;

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Request(List<String> request) {
        String[] reqLine = requestLineParser(request.getFirst());
        this.requestType = reqLine[0];
        this.path = reqLine[1];
        this.version = reqLine[2];

        for (int i = 1; i < request.size(); i++) {
            if (request.get(i).startsWith("Host")) {
                this.host = request.get(i).split("Host: ")[1];
            } else if (request.get(i).startsWith("User-Agent")) {
                this.userAgent = request.get(i).split("User-Agent: ")[1];
            } else if (request.get(i).startsWith("Accept")) {
                this.accept = request.get(i).split("Accept: ")[1];
            } else {
                this.body += request.get(i);
            }
        }
    }

    private String[] requestLineParser(String reqLine) {
        return reqLine.split(" ");
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestType='" + requestType + '\'' +
                ", path='" + path + '\'' +
                ", version='" + version + '\'' +
                ", host='" + host + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", accept='" + accept + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}