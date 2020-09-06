<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit product</title>
    <%@ include file="/WEB-INF/view/head.jsp" %>
</head>
<body>
<header>
    <%@ include file="/WEB-INF/view/header.jsp" %>
</header>
<article>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-7">
                <h1>Editing the product</h1>
                <form:form method="post" action="/admin/edit_product" modelAttribute="productDto">
                    <form>
                        <div class="form-group row">
                            <label for="name" class="col-sm-2 col-form-label">Name</label>
                            <div class="col-sm-10">
                                <form:input path="name" type="text" name="name"
                                            value="${productDto.name}" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="categoryDto" class="col-sm-2 col-form-label">Category</label>
                            <div class="col-sm-10">
                                <form:select path="categoryDto" cssClass="form-control">
                                    <form:options items="${categoriesDto}" itemValue="name" itemLabel="name"/>
                                </form:select>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="brand" class="col-sm-2 col-form-label">Brand</label>
                            <div class="col-sm-10">
                                <form:input path="brand" type="text" name="brand"
                                            value="${productDto.brand}" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="color" class="col-sm-2 col-form-label">Color</label>
                            <div class="col-sm-10">
                                <form:input path="color" type="text" name="color"
                                            value="${productDto.color}" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="weight" class="col-sm-2 col-form-label">Weight</label>
                            <div class="col-sm-10">
                                <form:input path="weight" name="weight" value="${productDto.weight}" min="1" max="99999"
                                            pattern="^[0-9]+$" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="height" class="col-sm-2 col-form-label">Height</label>
                            <div class="col-sm-10">
                                <form:input path="height" name="height" value="${productDto.height}" min="0" max="9999"
                                            pattern="^[0-9]+$" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="width" class="col-sm-2 col-form-label">Width</label>
                            <div class="col-sm-10">
                                <form:input path="width" name="width" value="${productDto.width}" min="0" max="9999"
                                            pattern="^[0-9]+$" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="depth" class="col-sm-2 col-form-label">Depth</label>
                            <div class="col-sm-10">
                                <form:input path="depth" name="depth" value="${productDto.depth}" min="0" max="9999"
                                            pattern="^[0-9]+$" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="upc" class="col-sm-2 col-form-label">UPC</label>
                            <div class="col-sm-10">
                                <form:input path="upc" name="upc" value="${productDto.upc}" min="1" max="999999999999"
                                            pattern="^[0-9]+$" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="inStock" class="col-sm-2 col-form-label">Quantity</label>
                            <div class="col-sm-10">
                                <form:input path="inStock" name="inStock" value="${productDto.inStock}" min="1"
                                            max="999" pattern="^[0-9]+$" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="price" class="col-sm-2 col-form-label">Price</label>
                            <div class="col-sm-10">
                                <form:input path="price" name="price" value="${productDto.price}" min="1" max="9999"
                                            pattern="^[0-9]+$" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="imagePath" class="col-sm-2 col-form-label">Image path</label>
                            <div class="col-sm-10">
                                <form:input path="imagePath" type="text" name="imagePath"
                                            value="${productDto.imagePath}" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-sm-10">
                                <form:input path="deleted" type="hidden" name="deleted"
                                            value="${productDto.deleted}" cssClass="form-control"/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-sm-10">
                                <button type="submit" class="btn btn-warning btn-lg">Edit</button>
                            </div>
                        </div>
                    </form>
                </form:form>
            </div>
        </div>
    </div>
</article>
</body>
</html>
