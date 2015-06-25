<?php
namespace APP\Module\Image;

use APP\Entity\Image;
use FSF\Helper\FileSystem;

class Generator
{
    const BO = "BO";
    const SD = "SD";
    const HD = "HD";
    const UHD = "UHD";

    public static $RESOLUTION_SIZE = array(
        self::SD  => array(
            "width" => 100,
            "height" => 60,
        ),
        self::HD  => array(
            "width" => 200,
            "height" => 120,
        ),
        self::UHD => array(
            "width" => 300,
            "height" => 180,
        ),
        self::BO  => array(
            "width" => 500,
            "height" => 300,
        ),
    );

    /** @var  Image */
    public $image;

    /**
     * image resize function
     * @param string $file               file name to resize
     * @param string $output             name of the new file (include path if needed)
     * @param int    $width              new image width
     * @param int    $height             new image height
     * @param bool   $delete_original    if true the original image will be deleted
     * @param bool   $use_linux_commands if set to true will use "rm" to delete the image, if false will use PHP unlink
     * @param int    $compression            0-9
     * @return bool
     */
    public static function resizeImageFile(
        $file,
        $output,
        $width,
        $height,
        $delete_original = false,
        $use_linux_commands = false,
        $compression = 9
    ) {

        if ($height <= 0 && $width <= 0) {
            return false;
        }
        if ($file === null || !file_exists($file)) {
            return false;
        }
        if ($output === "") {
            $output = $file;
        }

        // Setting defaults and meta
        $info = getimagesize($file);
        $image = '';
        list($width_old, $height_old) = $info;

        echo("<br/>width_old : ".$width_old);
        echo("<br/>height_old : ".$height_old);
        echo("<br/>width : ".$width);
        echo("<br/>height : ".$height);

        // Calculating proportionality
        $ratio_orig = $width_old / $height_old;
        $ratio_new = $width / $height;

        // Update size
        if($ratio_orig > $ratio_new){
            if ($width_old < $width) {
                $width = $width_old;
            }
            $height = $width / $ratio_orig;
        }else{
            if ($height_old < $height) {
                $height = $height_old;
            }
            $width = $height * $ratio_orig;
        }


        // This is the resizing/resampling/transparency-preserving magic
        $image_resized = imagecreatetruecolor($width, $height);
        imagefill($image_resized, 0, 0, imagecolorallocate($image_resized, 255, 255, 255));

        // Loading image to memory according to type
        switch ($info[2]) {
            case IMAGETYPE_JPEG:
                $image = imagecreatefromjpeg($file);
                break;
            case IMAGETYPE_GIF:
                $image = imagecreatefromgif($file);
                break;
            case IMAGETYPE_PNG:
                $image = imagecreatefrompng($file);
                break;
            default:
                return false;
        }
        imagecopyresampled($image_resized, $image, 0, 0, 0, 0, $width, $height, $width_old, $height_old);


        // Taking care of original, if needed
        if ($delete_original) {
            if ($use_linux_commands) {
                exec('rm ' . $file);
            } else {
                @unlink($file);
            }
        }

        imagepng($image_resized, $output, $compression);

        return true;
    }

    public function saveImage($image_file)
    {
        if (!$this->createDirectoriesTreeForImages()) {
            return false;
        }

        $this->image = new Image();
        $this->image->setNomImage(basename($image_file['name']));

        $target_file = self::getImagesDirectory() . $this->image->getNomImage();
        $imageFileType = strtolower(pathinfo($target_file, PATHINFO_EXTENSION));
        $uploadOk = true;
        // Allow certain file formats
        if ($imageFileType != "jpg"
            && $imageFileType != "png"
            && $imageFileType != "jpeg"
            && $imageFileType != "gif"
        ) {
            $uploadOk = false;
        }
        if ($uploadOk && move_uploaded_file($image_file["tmp_name"], $target_file)) {
            $this->image->save();

            return true;
        } else {
            return false;
        }
    }

    public static function getImagesDirectory()
    {
        return PUBLIC_DIR . "datas/images/";
    }

    public function resizeImage()
    {
        $file = self::getImagesDirectory() . $this->image->getNomImage();

        foreach (self::$RESOLUTION_SIZE as $res => $size) {
            $fileOutput = self::getImagesDirectory() . $res . '/' . $this->image->getNomImage();
            $fileOutput = substr($fileOutput, 0, strrpos($fileOutput, '.')) . '.png';
            $this->resizeImageFile($file, $fileOutput, $size['width'], $size['height']);
        }
    }

    private function createDirectoriesTreeForImages()
    {
        $return = true;
        $directories = str_replace(PUBLIC_DIR, "", self::getImagesDirectory());

        foreach (self::$RESOLUTION_SIZE as $res => $size) {
            $return = $return && FileSystem::createDirectoriesTree(PUBLIC_DIR, $directories . '/' . $res);
        }

        return $return;
    }
}