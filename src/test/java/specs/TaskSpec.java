package specs;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.with;
import static specs.BaseSpec.baseSpec;

public class TaskSpec {
    public static final RequestSpecification task = with()
            .spec(baseSpec);
}
