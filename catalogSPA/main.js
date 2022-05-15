var app = angular.module("myApp", []);

app.controller("myController", function($scope, $http, $window){

	// initialize the model's data (properties)
	//
	$scope.showList = false;
	$scope.showAdd = false;
	$scope.showEdit = false;

	$scope.selectedProduct = null;
								// product selected in the table

	$scope.param = {id: null, name: null, price: null, onSale: null};
								// representing data in the HTML form

	$scope.message = null;		// message for add and edit operations

	$scope.products = null;		// array of all products
										// maintained to be consistent with database


	/*	helper functions to work included HTML pages
	*/
	$scope.reset = function(){
		$scope.showList = false;
		$scope.showAdd = false;
		$scope.showEdit = false;
		$scope.showDelete = false;
	};

	$scope.showListPage = function(){

		$scope.reset();
		$scope.showList = true;
	};

	$scope.showAddPage = function(){
		$scope.reset();
		$scope.showAdd = true;
	};

	$scope.showEditPage = function(){
		$scope.reset();
		$scope.showEdit = true;
	};


	/*	To support edit and delete operation
		Called when we click to select a row in the table
		See the <tr> element that we use to display the table
	*/
	$scope.setSelectedProduct = function(product){
		$scope.selectedProduct = product;
	}


	/*	LOAD data - Called upon loading main page
	*/
	$scope.load = function() {

		// alert("Call getAllproducts");
		$scope.products = null;

		var connection = $http({
			method: "get",
			url: "http://localhost:8080/catalogWS/api/product"
		})

		.then(function(response){
			$scope.products = response.data;
		})

		.catch(function(response){
			$scope.message("Message for getAllproduct: Error - status: " + response.status);
		});

	};
	// end load


	/*	ADD a new product
	*/
	$scope.add = function() {

		$scope.param.id = null;
		$scope.param.name = null;
		$scope.param.price = null;
		$scope.param.onSale = null;

		$scope.showAddPage();
	};

	/*	Submit add product
	*/
	$scope.submitAdd = function() {

		var connection = $http(
			{
				method: "post",
				url: "http://localhost:8080/catalogWS/api/product",
				data:
					{
						"MISSING_PRICE":-1,
						"id": $scope.param.id,
						"name": $scope.param.name,
						"price": $scope.param.price,
						"onSale": $scope.param.onSale == null? false: true
					}
			})

		.then(function(response)
		{
			$scope.message = "Message for Add Product: Success - status: " + response.status;

			$scope.products.push(response.data); // add new data to products
		})

		.catch(function(response)
			{
				$scope.message = "Message for Add Product: Error - status: " +
					response.status + ": " + response.statusText;
			})

		.finally (function()
			{
				alert($scope.message);
				document.getElementById("addMessageHolder").innerHTML=$scope.message;
			});

	};
	// end add



	/*	EDIT selected product
	*/
	$scope.edit = function() {

		$scope.param.id = $scope.selectedProduct.id;
		$scope.param.name = $scope.selectedProduct.name;
		$scope.param.price = $scope.selectedProduct.price;
		$scope.param.onSale = $scope.selectedProduct.onSale;

		// alert("on sale of selected: " + $scope.selectedProduct.onSale +
		//	", type: " + typeof $scope.selectedProduct.onSale );

		$scope.showEditPage();
	};

	/*	Submit edit product
	*/
	$scope.submitEdit = function() {

		var connection = $http(
			{
				method: "put",
				url: "http://localhost:8080/catalogWS/api/product",
				data:
					{
						"MISSING_PRICE":-1,
						"id": $scope.param.id,
						"name": $scope.param.name,
						"price": $scope.param.price,
						"onSale": $scope.param.onSale
					}
			})

		.then(function(response)
		{
			// update the product in products
			var index = $scope.products.indexOf($scope.selectedProduct);
			$scope.products.splice(index, 1);
			$scope.products.push(response.data);

			$scope.message = "Message for Edit Product: Success - status: " + response.status;
		})

		.catch(function(response)
			{
				$scope.message = "Message for Edit Product: Error - status: " +
					response.status + ": " + response.statusText;
			})

		.finally (function()
			{
				alert($scope.message);
				document.getElementById("editMessageHolder").innerHTML=$scope.message;
			});
	};
	// end submit edit


	/*	DELETE selected product
	*/
	$scope.delete = function(product){

		var answer = confirm("Delete product with id = " + product.id);
		if(!answer){return;}

		// go ahead with delete
		$http({
			method: "delete",
			url: "http://localhost:8080/catalogWS/api/product?id=" + product.id
		})

		.then(function(response){

			var index = $scope.products.indexOf(product);
			$scope.products.splice(index, 1);

			$scope.message = "Message for Delete Product: Success - status: " + response.status;
			$scope.selectedProduct = null;
		})
		.catch(function(response){
			$scope.message = "Message for Delete Product: Error - status: " +
				response.status + ": " + response.statusText;

		})
		.finally(function(){
			alert($scope.message);
		});
	}
	// end delete


});
//end controller