$(function () {
    var d1 = [];
 ScheduleDataMB.scheduleDataArray.forEach( function(item, index) {
  d1.push([index, item]);
 });
    $.plot("#placeholder", [d1]);
});