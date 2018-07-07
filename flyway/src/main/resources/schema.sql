-- 重写 ; 为 // ，在spring中，注释掉下面一行，应该我们在配置文件中的 separator: // 便是起的该作用
-- DELIMITER //
-- 如果存在函数，则先删除
DROP PROCEDURE IF EXISTS `FUN20180706` //
-- 定义函数FUN20180706
CREATE PROCEDURE `FUN20180706` ()
    BEGIN
        DECLARE hasDataTable INT;
        SELECT count(*) INTO hasDataTable FROM information_schema.tables WHERE (table_schema = 'flywayDemo') AND (table_name= 'flyway_schema_history');
        IF hasDataTable = 0 THEN
           CREATE TABLE `flyway_schema_history` (
              `installed_rank` int(11) NOT NULL,
              `version` varchar(50) DEFAULT NULL,
              `description` varchar(200) NOT NULL,
              `type` varchar(20) NOT NULL,
              `script` varchar(1000) NOT NULL,
              `checksum` int(11) DEFAULT NULL,
              `installed_by` varchar(100) NOT NULL,
              `installed_on` timestamp NOT NULL DEFAULT current_timestamp(),
              `execution_time` int(11) NOT NULL,
              `success` tinyint(1) NOT NULL,
              PRIMARY KEY (`installed_rank`),
              KEY `flyway_schema_history_s_idx` (`success`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
          SET FOREIGN_KEY_CHECKS = 1;
        END IF;
    END
//

-- 调用函数
CALL FUN20180706() //
-- 恢复重写的;，以免影响其它的function
-- DELIMITER ;