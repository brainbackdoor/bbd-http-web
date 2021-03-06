package web.protocol.http.message.response;

import lombok.Builder;
import lombok.Getter;
import web.protocol.http.message.StartLine;
import web.protocol.http.message.common.Version;

import java.util.Objects;

@Getter
@Builder
public class ResponseLine implements StartLine {
    private static final String DELIMITER_RESPONSE_LINE = " ";
    private Version version;
    private StatusCode statusCode;
    private ReasonPhrase reasonPhrase;

    public ResponseLine(Version version) {
        this.version = version;
    }

    public ResponseLine(Version version, StatusCode statusCode) {
        this.version = version;
        this.statusCode = statusCode;
        this.reasonPhrase = statusCode.getReasonPhrase();
    }

    public ResponseLine(Version version, StatusCode statusCode, ReasonPhrase reasonPhrase) {
        this(version, statusCode);
        this.reasonPhrase = reasonPhrase;
    }

    public static ResponseLine of(Version version) {
        return new ResponseLine(version);
    }

    public static ResponseLine of(String input) {
        String[] splitValue = input.split(DELIMITER_RESPONSE_LINE);
        Version version = Version.of(splitValue[0]);
        StatusCode requestURL = StatusCode.of(Integer.parseInt(splitValue[1]));
        ReasonPhrase reasonPhrase = ReasonPhrase.of(splitValue[2]);
        return new ResponseLine(version, requestURL, reasonPhrase);
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseLine that = (ResponseLine) o;
        return version == that.version &&
                statusCode == that.statusCode &&
                Objects.equals(reasonPhrase, that.reasonPhrase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, statusCode, reasonPhrase);
    }
}
