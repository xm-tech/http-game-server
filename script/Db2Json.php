<?php
// dbtable => json
$host = "localhost";
$uname = "root";
$pwd = "xxxx";
$dbname = "demo_conf";
$con = mysqli_connect($host, $uname, $pwd, $dbname);
mysqli_query($con, "SET NAMES utf8mb4");

$tabs = array("t_sysconf", "t_shelf_level", "t_shelf", "t_item", "t_furniture", "t_equip_unit", "t_equip", "t_level_selltype", "t_equips_capacity_expand", "t_wholesale_market",
    "t_logistics", "t_logistics_level");
foreach ($tabs as $t) {
    $sql = "select * from $t";
    echo "$sql\t";
    $ret = mysqli_query($con, $sql);
    $rows = array();
    while ($r = mysqli_fetch_assoc($ret)) {
        if ($t == "t_sysconf") {
            $rows[$r['name']] = $r['value'];
        } else if ($t == "t_level_selltype") {
            $rows[$r['level']] = $r['selltype'];
        } else if ($t == "t_equips_capacity_expand") {
            $rows[$r['expand_num']] = $r;
        } else if ($t == "t_logistics_level") {
            $rows[$r['level']] = $r;
        } else {
            $rows[$r['id']] = $r;
        }
    }
    $fname = "json/$t.json";
    if ($t == "t_logistics") {
        $t_json = json_encode($rows, JSON_FORCE_OBJECT);
    } else {
        $t_json = json_encode($rows, JSON_UNESCAPED_UNICODE);
    }
    echo "gen $fname\n";
    file_put_contents($fname, $t_json);
}
mysqli_close($con);
?>
