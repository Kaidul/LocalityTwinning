<?php
/**
 * The base configurations of the WordPress.
 *
 * This file has the following configurations: MySQL settings, Table Prefix,
 * Secret Keys, WordPress Language, and ABSPATH. You can find more information
 * by visiting {@link http://codex.wordpress.org/Editing_wp-config.php Editing
 * wp-config.php} Codex page. You can get the MySQL settings from your web host.
 *
 * This file is used by the wp-config.php creation script during the
 * installation. You don't have to use the web site, you can just copy this file
 * to "wp-config.php" and fill in the values.
 *
 * @package WordPress
 */

// ** MySQL settings - You can get this info from your web host ** //
/** The name of the database for WordPress */
define('DB_NAME', 'locality_twinning');

/** MySQL database username */
define('DB_USER', 'root');

/** MySQL database password */
define('DB_PASSWORD', '');

/** MySQL hostname */
define('DB_HOST', 'localhost');

/** Database Charset to use in creating database tables. */
define('DB_CHARSET', 'utf8');

/** The Database Collate type. Don't change this if in doubt. */
define('DB_COLLATE', '');

/**#@+
 * Authentication Unique Keys and Salts.
 *
 * Change these to different unique phrases!
 * You can generate these using the {@link https://api.wordpress.org/secret-key/1.1/salt/ WordPress.org secret-key service}
 * You can change these at any point in time to invalidate all existing cookies. This will force all users to have to log in again.
 *
 * @since 2.6.0
 */
define('AUTH_KEY',         'Qw>xP]uv`2*SmJ%LK5_0QBX5[*QHcQ6v`>lG(i(^~g]qN2:#7u*KHi~>mMhF@}+[');
define('SECURE_AUTH_KEY',  'WPEDEn`W%dYg8{2K,a Fx|X0LOq-7yQ`4Ne^Hr),L2F[{z)xV-vZ&)Y#T:KJV~F#');
define('LOGGED_IN_KEY',    'T;le}EX_ <6p]; I>N$s{Xqz;YNiH$obbJw.})69ExP%D?=4hcA{^iNCz_(46t!B');
define('NONCE_KEY',        '|D/{!3,2QwTdT-n0qU6XrOIC607V=T^TnTrI5^BM?3~;*tNaeekY}@@ETuC0|VdX');
define('AUTH_SALT',        'w5[+>h(NlfCt~wAc[/~Px7;YcExSgb?%K{/cJ$~v:)>*8tbd&!-VR&h1M|l~jx!/');
define('SECURE_AUTH_SALT', 'U2}a,~wY@~pA&t|U8J:$zl8EAi0@_5,w}cDFN/R7+,!Hwf$EdB/$x.f&_V?Jn/bg');
define('LOGGED_IN_SALT',   ',ve1KL0G@?*nuN9 m*?jhUYiYij6*$|l hH2[DPK( VqxNVt^iHEEa1fc/Xh/NtP');
define('NONCE_SALT',       'ww[[%}^km< kz%s-cDvBUY-:Q3u=u]:G#h]n7/dc~Cg5.>Fh5>rz/zrW`a9uLnji');

/**#@-*/

/**
 * WordPress Database Table prefix.
 *
 * You can have multiple installations in one database if you give each a unique
 * prefix. Only numbers, letters, and underscores please!
 */
$table_prefix  = 'twinning_';

/**
 * WordPress Localized Language, defaults to English.
 *
 * Change this to localize WordPress. A corresponding MO file for the chosen
 * language must be installed to wp-content/languages. For example, install
 * de_DE.mo to wp-content/languages and set WPLANG to 'de_DE' to enable German
 * language support.
 */
define('WPLANG', 'bn_BD');

/**
 * For developers: WordPress debugging mode.
 *
 * Change this to true to enable the display of notices during development.
 * It is strongly recommended that plugin and theme developers use WP_DEBUG
 * in their development environments.
 */
define('WP_DEBUG', false);

/* That's all, stop editing! Happy blogging. */

/** Absolute path to the WordPress directory. */
if ( !defined('ABSPATH') )
	define('ABSPATH', dirname(__FILE__) . '/');

/** Sets up WordPress vars and included files. */
require_once(ABSPATH . 'wp-settings.php');
