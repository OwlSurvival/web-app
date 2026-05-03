//alert("Привет")
window.CATS = {
 addData: function () {

   var catName = document.getElementById('catName').value;
   var timstamp = document.getElementById("timestamp").value;
   var catWeidth = document.getElementById("weith_cat").value;
   var eatName = document.getElementById("eatName").value;
   var weith_eat = document.getElementById("weith_eat").value;
   var happy_unhappy = document.getElementById("happy_unhappy").value;

   var result= {
     "id":crypto.randomUUID(),
     "name": catName,
     "time": timstamp,
     "catWeidth": catWeidth,
     "eatName": eatName,
     "weith_eat": weith_eat,
     "happy_unhappy": happy_unhappy
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
    newRow.innerHTML = '<td>'+arrayData[i].name+'</td><td>'+arrayData[i].time+
    '</td><td>'+arrayData[i].catWeidth+' кг</td><td>'+arrayData[i].eatName+
    '</td><td>'+arrayData[i].weith_eat+'г</td><td>'+arrayData[i].happy_unhappy+
    '</td>';
    tbody.appendChild(newRow);

   }
 },
clearData: function (){
  localStorage.setItem("arrayKey","[]");
  CATS.drowTable();
}

}
