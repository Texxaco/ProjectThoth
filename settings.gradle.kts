rootProject.name = "Project Thoth"
include("core")

include("serializer")
include("serializer:json")
findProject(":serializer:json")?.name = "json"

include("persistence")
include("persistence:spring")
findProject(":persistence:spring")?.name = "spring"
include("persistence:spring:json")
findProject(":persistence:spring:json")?.name = "json"


include("ruleengines")
include("ruleengines:homegrow")
include("ruleengines:homegrow:core")
findProject(":ruleengines:homegrow:core")?.name = "core"
include("ruleengines:homegrow:serializer")
findProject(":ruleengines:homegrow:serializer")?.name = "serializer"
include("ruleengines:homegrow:serializer:json")
findProject(":ruleengines:homegrow:serrializer:json")?.name = "json"

include("renderer")
include("renderer:xml-fo")
include("renderer:xml-fo:core")
findProject(":renderer:xml-fo:core")?.name = "core"
include("renderer:xml-fo:serializer")
findProject(":renderer:xml-fo:serializer")?.name = "serializer"
include("renderer:xml-fo:serializer:json")
findProject(":renderer:xml-fo:serializer:json")?.name = "json"

include("inttest")
