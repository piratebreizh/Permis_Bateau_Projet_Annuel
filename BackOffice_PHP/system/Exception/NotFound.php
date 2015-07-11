<?php
namespace FSF\Exception;

use FSF\Exception;

class NotFound extends Exception
{
    public function __construct()
    {
        header('Location: /erreur/404');
    }
}