<?php

class Config
{
    public static function paramConnexionDb()
    {
        return array(
            'dbname' => 'permis_bateau',
            'user' => 'root',
            'password' => '',
            'host' => 'localhost',
            'port' => 3306,
            'charset' => 'utf8',
            'driver' => 'pdo_mysql',
        );
    }
}