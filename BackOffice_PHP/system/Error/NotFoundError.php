<?php

namespace FSF\Error;

use FSF\Exception;

class NotFoundError extends Exception
{
    public function __construct()
    {
        $this->message = 'La page est introuvable.';

        // On génère une alerte par précaution
        parent::__construct($this->message, E_WARNING);

        header('HTTP/1.0 404 Not Found');
    }
}