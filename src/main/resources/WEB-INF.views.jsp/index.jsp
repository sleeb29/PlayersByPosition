<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Baseball Player Services</title>
    <spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss"/>
</head>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Baseball Player Service</a>
        </div>
    </div>
</nav>

<div class="jumbotron">
    <div class="container">
        <p>
            Available Services:
        </p>
        <p>
            <a class="btn btn-primary btn-lg" href="getStartersByPositionWorkbook" role="button">Starters by
                Position</a>
        </p>
    </div>
</div>

</html>
