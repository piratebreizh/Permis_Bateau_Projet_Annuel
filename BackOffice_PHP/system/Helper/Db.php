<?php

namespace FSF\Helper;

use Doctrine\DBAL\DriverManager;

abstract class Db
{
    /**
     * Doctrine instance
     * @var \Doctrine\DBAL\Connection
     */
    protected static $instance;

    /**
     * Singleton - private construct
     */
    private function __construct()
    {

    }

    /**
     * Return the Doctrine instance for database connection
     * @return \Doctrine\DBAL\Connection
     * @throws \Doctrine\DBAL\DBALException
     */
    public static function getInstance()
    {
        if (is_null(self::$instance)) {
            $config = new \Doctrine\DBAL\Configuration();
            $params = \Config::paramConnexionDb();

            self::$instance = DriverManager::getConnection($params, $config);
        }

        return self::$instance;
    }
}
