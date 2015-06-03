<?php

namespace FSF\Response\Header;

class Status extends Header
{
    const TYPE = 'Status';

    const VALUE_OK = '200 OK';
    const VALUE_FORBIDDEN = '403 Forbidden';
    const VALUE_NOT_FOUND = '404 Not Found';

    /**
     * @param string $value
     */
    public function __construct($value = self::VALUE_OK)
    {
        $this->setValue($value);
    }

    /**
     * @return $this
     */
    public function render()
    {
        header('HTTP/1.1 ' . $this->getValue());

        return $this;
    }

    /**
     * Type for the header. Can be used to determine header to send
     * @return string
     */
    public function getType()
    {
        return self::TYPE;
    }
}
