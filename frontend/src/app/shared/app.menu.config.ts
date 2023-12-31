import { MenuRootItem } from 'ontimize-web-ngx';

export const MENU_CONFIG: MenuRootItem[] = [
  { id: 'home', name: 'HOME', icon: 'home', route: '/main/home' },
  { id: 'registros', name: 'REGISTERS', icon: 'list', route: '/main/registros' },
  { id: 'resumeDelivery', name: 'RESUME', route: '/main/resume-delivery', icon: 'bar_chart'},
  { id: 'trucks', name: 'TRUCKS', route: '/main/trucks', icon: 'local_shipping' },
  { id: 'order', name: 'ORDER', route: '/main/order', icon: 'assignment' },
  { id: 'config', name: 'CONFIG', route: '/main/config', icon: 'settings' },
  { id: 'logout', name: 'LOGOUT', route: '/login', icon: 'power_settings_new', confirm: 'yes' },
 
];
