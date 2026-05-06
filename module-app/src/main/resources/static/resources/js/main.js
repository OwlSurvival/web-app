//alert("Привет")
window.CATS = {
 addData: function () {

   var catName = document.getElementById('catName').value;
   var datetime = document.getElementById("timestamp").value;
   var weight = document.getElementById("weith_cat").value;
   var eatName = document.getElementById("eatName").value;
   var eatWeight = document.getElementById("weith_eat").value;
   var happy = document.getElementById("happy_unhappy").value;

   var result= {
     "id":crypto.randomUUID(),
     "name": catName,
     "datetime": datetime,
     "weight": weight,
     "eatName": eatName,
     "eatWeight": eatWeight,
     "happiness": happy
   }
   var arrayData = localStorage.getItem("arrayKey");
   if (!arrayData){
     arrayData=[];
   }else{
     arrayData=JSON.parse(arrayData);
   }

   arrayData.push(result);
   localStorage.setItem("arrayKey",JSON.stringify(arrayData));


    console.log(arrayData);
    CATS.drowTable();

 },
 drowTable: function (){
   const tbody = document.getElementById('catTableBodyId');
   tbody.innerHTML = '';

   var arrayData = JSON.parse(localStorage.getItem("arrayKey"));
   for (var i = 0; i < arrayData.length; i++) {
    console.log(arrayData[i]);

    var newRow = document.createElement('tr');
    newRow.innerHTML = '<td>'+arrayData[i].name+'</td><td>'+arrayData[i].datetime+
    '</td><td>'+arrayData[i].weight+' кг</td><td>'+arrayData[i].eatName+
    '</td><td>'+arrayData[i].eatWeight+'г</td><td>'+arrayData[i].happiness+
    '</td>';
    tbody.appendChild(newRow);

   }
 },
 clearData: function (){
    localStorage.setItem("arrayKey","[]");
    CATS.drowTable();
 },

 storeToDataBase: async function(){
      try {
              var response = await fetch('/cats/insert-cat-records', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: localStorage.getItem("arrayKey")
              });
              if (response.ok) {
                const data = await response.json;
                console.log("response:"+data);
               // CATS.clearData();
              } else {
                throw new Error('Request failed with status ${response.status}');
              }
      } catch (error) {
          console.error(error.message);
      }

 },
 getDataFromBase: async function(){
      try {
              var response = await fetch('/cats/insert-cat-records', {
                method: 'GET',
                headers: {'Content-Type': 'application/json'}
              });
              if (response.ok) {
                const data = await response.json;
                console.log("response:"+data);
               // CATS.clearData();
              } else {
                throw new Error('Request failed with status ${response.status}');
              }
      } catch (error) {
          console.error(error.message);
      }

 }

}
